package com.lovejjfg.blogdemo.ui;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lovejjfg.blogdemo.R;

/**
 * Created by Administrator on 2015/11/8.
 */
public class TopSlidWebViewLayout extends FrameLayout {

    private View topMsgView;
    private TopSlidWebView mWebView;
    private WebViewLayout mWebViewLayout;
    //    private FrameLayout mFrameLayout;

    public TopSlidWebViewLayout(Context context) {
        this(context, null);
    }

    public TopSlidWebViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopSlidWebViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        topMsgView = LayoutInflater.from(context).inflate(R.layout.top_msg, null);
        mWebView = new TopSlidWebView(context);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        addView(topMsgView, 0, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        mFrameLayout = new FrameLayout(context);
        mWebViewLayout = new WebViewLayout(context);
        addView(mWebViewLayout, 1, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public View getTopMsgView() {
        return topMsgView;
    }

    public TopSlidWebView getTopSlidWebView() {
        return mWebViewLayout.getTopSlidWebView();
    }

    public void setTopMsg(CharSequence msg) {
        ((TextView) topMsgView.findViewById(R.id.msg1)).setText(msg);
    }
}
