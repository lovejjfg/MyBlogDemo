package com.lovejjfg.blogdemo.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
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
public class BrowserActivity extends BaseSlideFinishActivity {

    private TextView mAppTitlle;
    private TopSlidWebView mTopSlidWebView;
    private TopSlidWebViewLayout mSlidWebViewLayout;
    private ProgressBar mPb;

    @Override
    public View initView(Bundle savedInstanceState) {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_browser, null);
        mSlidWebViewLayout = (TopSlidWebViewLayout) view.findViewById(R.id.web_view_layout);
        mPb = (ProgressBar) view.findViewById(R.id.pb);
        mTopSlidWebView = mSlidWebViewLayout.getTopSlidWebView();
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
            WebChromeClient webChromeClient = new WebChromeClient() {

                @Override
                public void onProgressChanged(WebView view, int newProgress) {
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
            };

            WebViewClient webViewClient = new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    mSlidWebViewLayout.setTopMsg(view.getTitle());
                }

                public void onReceivedError(WebView view, int errorCode,
                                            String description, String failingUrl) {
                    Log.e("ProcessPayment", "onReceivedError = " + errorCode);

                    //404 : error code for Page Not found
                    if(errorCode==404){
                        // show Alert here for Page Not found
                        view.loadUrl("file:///android_asset/html/404.html");
                    }
                    else{
                        view.loadUrl("file:///android_asset/html/nowifi.html");

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
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    mPb.setVisibility(View.VISIBLE);
                }
            };
            mTopSlidWebView.setWebChromeClient(webChromeClient);

            mTopSlidWebView.setWebViewClient(webViewClient);

            mTopSlidWebView.loadUrl("https://www.baidu.com/");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTopSlidWebView.destroy();
    }


}
