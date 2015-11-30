#Android WebView Cookie的相关设置和自定义错误页面加载

这个是`WebView`的后篇吧，一拖又不知道拖了多久了，言归正传，上一次大概_翻译_了一些WebView的官方介绍，然后剩下了`WebView`的Cookie相关另外就是加载页面错误那个丑丑的页面的处理。


_内容简介：_

* WebView Cookie的相关使用!

* 错误页面的处理


华丽的分割线

---------------------------------

`Cookie`在请求接口的时候往往需要有要求，就是需要你带上用户的`Token`,`id`...等等的东西。

啦啦啦，直接看一个方法吧：

	 @SuppressLint("NewApi")
    private void setCookie(String url) {
        URI uri = URI.create(url);

        if (TextUtils.isEmpty(uri.getHost())) {
            return;
        }

        if (!Build.VERSION.SDK_INT >= 21) {
            CookieSyncManager.createInstance(mContext);
        }

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        // 注入cookies
        List<String> cookies = getCookies();
        for (String cookie : cookies) {
            cookieManager.setCookie(uri.getHost(), cookie);
        }

          if (Build.VERSION.SDK_INT >= 21) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }

你没有看错，就是酱紫的！！通过`CookieManager`得到相关的管理器。然后乎乎地往里面加就是了！

**添加Cookie一定要记得先对Url进行判断，你请求自己的接口固然需要带上自己的cookie，但是要是你请求第三方的支付等等的接口，然后你又盲目的携带上别人没有指定的cookie，或者覆盖了别人的cookie，那么就呵呵哒了！（我在这里就被坑过，一请求网页就过期，后来才知道在loadUrl()这个方法时，都强加了cookie的！！）**


然后就是错误页面的处理了，实现也比较简单，那就是在网页加载错误时候指定一个本地的页面加载，另外在加载的时候首先判断一下当前的网络状态是否可用。

	 WebViewClient webViewClient = new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    mSlidWebViewLayout.setTopMsg(view.getTitle());
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    super.onReceivedSslError(view, handler, error);
                }

                public void onReceivedError(WebView view, int errorCode,
                                            String description, String failingUrl) {
                    Log.e("onReceivedError", "onReceivedError = " + errorCode);

                    //404 : error code for Page Not found
                    if (errorCode == 404) {
                        // show Alert here for Page Not found
                        view.loadUrl("file:///android_asset/html/404.html");
                        Log.e("404", failingUrl);
                    } else {
                        view.loadUrl("file:///android_asset/html/nowifi.html");
                        Log.e("error", failingUrl);

                    }
                }

                @Override
                public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                    super.onReceivedHttpError(view, request, errorResponse);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    mTopSlidWebView.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageStarted(final WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    mPb.setVisibility(View.VISIBLE);
                }
            };
            mTopSlidWebView.setWebChromeClient(webChromeClient);


然后在loadUrl执行之前，可以做一些处理了：

	   @Override
    public void loadUrl(String url) {
		//记录之前的一个url
        if (mUrl == null || !mUrl.equals(url)) {
            mUrl = url;
        }
		//url是需要添加cookie的
		if (url.startsWith("xxxx")) {
            setCookie(url);
        }
		//网络是否可用
        mNetworkInfo = connectionManager.getActiveNetworkInfo();
        super.loadUrl(mNetworkInfo == null ? "file:///android_asset/html/nowifi.html" : url);
    }

最后最后：那么跳转为本地assets里的html之后，又怎么让用户点击重新加载呢？这里就又要用到之前说的js和本地的交互了：

跟着步奏走：

1. 定义一个js接口：

		public interface CallBack {
	        @JavascriptInterface
	        void goBack();
	    }

2. `WebView`添加接口:
	
			//设置js可用 别忘记了
		 settings.setJavaScriptEnabled(true);
			//添加接口，第二个参数要和html里面的那个window.xxx.goback()保持一致，对应的对象嘛！！
	        addJavascriptInterface(new CallBack() {
	            @JavascriptInterface
	            public void goBack() {
			//   reload();
	                mHandler.sendEmptyMessage(RELOAD);
	                Toast.makeText(getContext(), "call back", Toast.LENGTH_SHORT).show();
	            }
	        }, "android");

3. 在接口里面不能直接reload（），或者loadUrl(),那么就handler一下吧！

		 private Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            if (msg.what == RELOAD) {
	                loadUrl(mUrl);
	
	                Log.e("reloadurl", mUrl);
	            }
	            super.handleMessage(msg);
	        }
	    };
	

至此，错误页面和Cookie的相关设置都搞定了！！！

[相关Demo请戳这里！！！！](https://github.com/lovejjfg/MyBlogDemo)