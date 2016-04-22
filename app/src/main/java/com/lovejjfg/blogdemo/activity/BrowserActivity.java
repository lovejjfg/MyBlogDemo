package com.lovejjfg.blogdemo.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.base.BaseSlideFinishActivity;
import com.lovejjfg.blogdemo.ui.TopSlidWebView;
import com.lovejjfg.blogdemo.ui.TopSlidWebViewLayout;

/**
 * Created by Administrator on 2015/11/8.
 */
public class BrowserActivity extends BaseSlideFinishActivity  {

    private TextView mAppTitlle;
    private TopSlidWebView mTopSlidWebView;
    private TopSlidWebViewLayout mSlidWebViewLayout;
    private ProgressBar mPb;
    //    private Timer timer;
//    private TimerTask tt;
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public View initView(Bundle savedInstanceState) {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_browser, null);
        mSlidWebViewLayout = (TopSlidWebViewLayout) view.findViewById(R.id.web_view_layout);
        mPb = (ProgressBar) view.findViewById(R.id.pb);
        mTopSlidWebView = mSlidWebViewLayout.getTopSlidWebView();
//        WebView.setWebContentsDebuggingEnabled(true);

        Toolbar mToolBar = (Toolbar) view.findViewById(R.id.app_bar);
        setSupportActionBar(mToolBar);
        ActionBar bar = getSupportActionBar();//.setDisplayHomeAsUpEnabled(true);
        if (null != bar) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(false);
        }
        mToolBar.setNavigationIcon(R.mipmap.ic_arrow_back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishSelf();
            }
        });

        mAppTitlle = (TextView) view.findViewById(R.id.app_tittle);
        mAppTitlle.setText(R.string.name);
        return view;//LayoutInflater.from(this).inflate(R.layout.activity_browser, null);
    }

    @Override
    protected void performViewClick(View v) {
//        switch (v.getId()) {
//            case R.id.app_bar:
//
//                break;
//        }
    }

    @Override
    protected void init() {
        super.init();


        if (null != mTopSlidWebView) {
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    mTopSlidWebView.stopLoading();
                }
            };

            WebChromeClient webChromeClient = new WebChromeClient() {

                @Override
                public void onProgressChanged(WebView view, final int newProgress) {
                    super.onProgressChanged(view, newProgress);

                    if (newProgress == 100) {
                        mPb.setVisibility(View.GONE);
                    } else {
                        mPb.setProgress(newProgress);
                    }
                }

                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    mSlidWebViewLayout.setTopMsg(title);
                }


                @Override
                public void onShowCustomView(View view, CustomViewCallback callback) {
                    super.onShowCustomView(view, callback);

                }

                @Override
                public void onHideCustomView() {
                    super.onHideCustomView();
                }

                @Override
                public View getVideoLoadingProgressView() {
                    return super.getVideoLoadingProgressView();
                }
            };

            WebViewClient webViewClient = new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    mSlidWebViewLayout.setTopMsg(view.getTitle());
                }

//                @Override
//                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                    super.onReceivedError(view, request, error);
//                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    super.onReceivedSslError(view, handler, error);
                }

                public void onReceivedError(WebView view, int errorCode,
                                            String description, String failingUrl) {
                    Log.e("ProcessPayment", "onReceivedError = " + errorCode);

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

//                @Override
//                public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
//                    super.onReceivedHttpError(view, request, errorResponse);
//                }

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
            View loadingProgressView = webChromeClient.getVideoLoadingProgressView();


            mTopSlidWebView.setWebViewClient(webViewClient);
//            mTopSlidWebView.loadUrl("http://v.baidu.com/i");
//            mTopSlidWebView.loadData(getString(R.string.html_body),
//                    "text/html", "UTF-8");
            mTopSlidWebView.loadDataWithBaseURL(null, getString(R.string.html_body), "text/html", "UTF-8", null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTopSlidWebView.destroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
