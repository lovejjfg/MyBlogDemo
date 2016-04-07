* 1.第一个问题是调用代扣接口时，由于自己定义的WebView携带了自己相关的cookie，导致请求支付页面的失效。
	webView的post只有那个 mWebview.postUrl(mUrl, params.getBytes()) 的方法.

* 2.关于WebView的浏览记录的管理，可以通过`WebBackForwardList webBackForwardList = mWebview.copyBackForwardList()`的方法的到的它的浏览记录。**但是，这个得到的只是一个拷贝，无法真实的修改！**所以后来采用了`mWebview.clearHistory()`的方法。
而`mWebview.clearHistory()`该方法需要在下一个页面加载成功之后才执行。
 
		WebViewClient webViewClient = new WebViewClient() {
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            BrowserUtil.startActivityByBrowser(BrowserActivity.this, url);
	            return true;
	        }

	        @Override
	        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
	            handler.proceed();
	        }

	        @Override
	        public void onPageFinished(WebView view, String url) {
	            super.onPageFinished(view, url);
	            if (setReceivedTitle) {
	                mTvTitle.setText(view.getTitle());
	            }
	            if ("https://ibank-peps-stg.pingan.com.cn/peps/work/phone_html/index.html".equals(url) ) {
	                mWebview.clearHistory();
	            }
	        }
	};

* 3.收银台Dialog的莫名消失问题，这个涉及的是封装的共用方法，`showLoadingDialog(Context context)`;
因为这个里面差不多就是单例的意思，而且每一次的show()；都会把上一个给覆盖掉！所以在那种需要返回并保留状态的页面，dialog出现了异常。
解决方法：**直接new一个新的dialog，不使用那个共用的方法！然后增加对Activity生命周期的判断，对应关闭dialog.....**

* 4.给WebView设置tittle的方法：

	
		WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            BrowserUtil.startActivityByBrowser(BrowserActivity.this, url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (setReceivedTitle) {
                mTvTitle.setText(view.getTitle());
            }
            if ("https://ibank-peps-stg.pingan.com.cn/peps/work/phone_html/index.html".equals(url) ) {
                mWebview.clearHistory();
            }
        }
  	  	};

    	WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) mBtnLoading.setVisibility(View.GONE);
            else if (newProgress == 0) mBtnLoading.setVisibility(View.VISIBLE);
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (setReceivedTitle) {
                mTvTitle.setText(title);
            }
        }
    	};

* 5.`Fragment`里的`setUserVisibleHint()`,可用于懒加载。
	
		 @Override
	    public void setUserVisibleHint(boolean isVisibleToUser) {
	        super.setUserVisibleHint(isVisibleToUser);
	        if(getUserVisibleHint()) {
	            isVisible = true;
	            onVisible();
	        } else {
	            isVisible = false;
	            onInvisible();
	        }
	    }
	    protected void onVisible(){
	        lazyLoad();
	    }
	    protected abstract void lazyLoad();
让子类去复写这个方法，做判断。
其实也可以使用那个判断当前被选的方法，只有被选择了，才去初始化数据！

* 6.在showLoadingDialog()的方法点击home键退出程序后，一系列的异常情况！

context.startActivity()的方法一定是让自己先出于可见状态吗？生命周期已经到了onResume()，可是界面如果是用点击应用程序的方式进入还是停留在上一个页面。如果查看后台历史程序点击进入就会进入到下一个界面，但是下一个界面或多或少会存在一些问题，（背景是黑色的！）

home键之后的fragment的生命周期是

fragment暂停！！
fragement停止了！！
fragment所在的activity.stop!!!!!!!!!!!!!!
新的activity创建了了
新的activity获取焦点了了
fragment所在的activity重新开始！！
fragment开始！！
fragment所在的activityactivity开始！！

可以理解为点击图标启动时，系统由于某种情况没有重新绘制页面？逻辑是完全可用的，即，虽然你看不到这个页面，但是这个页面真是的存在！！

* 7.Dialog相关问题的始末：

`dialog`的`close`方法是**直接暴力的关闭**。
在`BaseActivity`的`onDestroy()`方法中也有关闭的动作，其实这些个都还没有问题，但是，如果关闭的方法是走的`handler`，`MessageQuery`中一个一个的取，那么就有可能出现问题了。A页面跳转B页面，dialog正常显示，B返回走onDestroy()方法，关闭的方法放到了消息池中，然后又马上从A页面又开启B页面，那么问题就来了，dialog也走了show的方法，但是就是不出现相关的文字，打Log你也会发现，LOG显示了很久了，对应的逻辑才执行到位！**都是handler机制或者说都是注解框架的影响。**

_Prior to 3.0, UiThread annotated method calls was always added in the handler execution queue to ensure that execution was done in Ui thread. In 3.0, we kept the same behavior for compatibility purpose.
But, if you want to optimize(优化) UiThread calls, you may want to change propagation() value to REUSE. In this configuration, the code will make a direct call to the method if current thread is already Ui thread. If not, we're falling back to handler call._


	if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            BaseUtil_.super.closeLoadingDialog();
            return ;
        }
        handler_.post(new java.lang.Runnable() {


            @Override
            public void run() {
                BaseUtil_.super.closeLoadingDialog();
            }

        }
        );

* 8.只要fragment被add了Activity中,即使hide的了，也会跟着Activity的生命周期调用相关的方法。。。。


* 9.本地的数据应该根据网络返回动态加载。（2015.10.16）

* 10.eventBus的使用，其实和广播机制类似。一对多的情况。只是还没有明白那个`onEvent(EventBusBean event)`的方法和`onEventMainThread(EventBusBean event)`都不需要重写父类？这个方法写出来就可以用？！还有**EventBus使用的地方是不能加入混淆的，因为它只认识这两个方法名称？！**

* 11.代码中获取资源文件中的**字符串**，可以直接通过`getString(int id)`的方法。

* 12.自定义WebView cookie的写法：


* 13.window全屏设置

		//onCreate（）方法中，setContentView（）之前：
		getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//配置文件中对应配置：
		android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
* 14.自定义shape:(API Guides>Resource Types>Drawable)


		<shape xmlns:android="http://schemas.android.com/apk/res/android"
	    android:shape="rectangle">
	    <gradient
	        android:startColor="#FFFF0000"
	        android:endColor="#80FF00FF"
	        android:angle="45"/>
	    <padding android:left="7dp"
	        android:top="7dp"
	        android:right="7dp"
	        android:bottom="7dp" />
	    <corners
	        android:bottomLeftRadius="10dp"
	        android:bottomRightRadius="10dp"
	        />
		</shape>

* 15.LinearLayout中可以使用divider的属性给子View做分割。

* 16.在selector中可以添加很多定义的，不局限于**state item**!


* 17.APP升级的相关bug，升级后本地的`token`没有更新，然后就会使用老版本的**UserInfo**，而在新版本中会添加一些字段，老版本的没有那些字段，然后使用就会出现 null....

* 18.安安租`Json`封装的相关优化：

		  BaseEntity baseEntity = JsonUtil.parseObject(result, BaseEntity.class);

		if (baseEntity.isOk()) {
                    if (baseEntity.getData().isEmpty()) {
                        if (errorHandler != null) {
                            errorHandler.onCodeError(baseEntity.getCode(), baseEntity.getMsg());
                        }
                    } else {
                        bean = JsonUtil.parseObject(baseEntity.getData().toJSONString(), entityClass);
                    }
                    bean = JsonUtil.parseObject(baseEntity.getData().toJSONString(), entityClass);
                }




		if (url.equals("test")) {
                result=  FileUtil.getFromAssets(mContext, "test");
				DevUtil.v("will", "result===== " + result);
            } else {
				result = requestBase(method, hostUrl, url, params, handle,
						isHttpsRequest);
			}
			entity = JsonUtil.parseObject(result, entityClass);

然后对应的`bean`对象的不同：



		//bean对象必须继承BaseEntity,然后里面定义一个data对象；
		public class LouPanListEntity extends BaseEntity {
	
	    private LouPanListDataEntity data;
	
	    public LouPanListEntity() {
	    }
	
	    public LouPanListEntity(String errorMessage) {
	        super(errorMessage);
	    }
	
	    public LouPanListDataEntity getData() {
	        return data;
	    }
	
	    public void setData(LouPanListDataEntity data) {
	        this.data = data;
	    }
	
	    /**
	     * 楼盘列表数据数据实体
	     * 
	     * @author zhangjiao
	     * 
	     */
	    public static class LouPanListDataEntity {}


		//直接封装那个对应的data对象
		public class OrderInfoBean implements Parcelable {
	
		    private String sTradeNo;
		
		    private int iTotalAmount;
		
		    private String sSubject;
		
		    private int iCredit;
		
		    private int iCanUseCredit;
		
		    private ArrayList<RedbagBean> aCoupon;
		
		    private ArrayList<PayInfo> aPayInfo;
			
		.....}
	




