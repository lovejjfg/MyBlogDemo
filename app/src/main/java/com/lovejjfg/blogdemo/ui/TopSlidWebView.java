package com.lovejjfg.blogdemo.ui;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/8.
 */
public class TopSlidWebView extends WebView {

    private static final int RELOAD = 0;
    private int startY;
    private int dy;
    private WebViewLayout mWebViewLayout;
    private ConnectivityManager mNetworkStatsManager;
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo mNetworkInfo;
    private ConnectivityManager connectionManager;
    private String mUrl;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == RELOAD) {
                loadUrl(mUrl);

                Log.e("tag", mUrl);
            }
            super.handleMessage(msg);
        }
    };

    public TopSlidWebView(Context context) {
        this(context, null);
    }

    public TopSlidWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopSlidWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    private void init(Context context) {
        post(new Runnable() {
            @Override
            public void run() {
                mWebViewLayout = (WebViewLayout) getParent();
//                topMsgView = mWebViewLayout.getTopMsgView();
            }
        });
        connectionManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);


        WebSettings settings = getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDisplayZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setTextZoom(200);
//        initSize(settings);
        addJavascriptInterface(new CallBack() {
            @JavascriptInterface
            public void goBack() {
//                reload();
                mHandler.sendEmptyMessage(RELOAD);
                Toast.makeText(getContext(), "call back", Toast.LENGTH_SHORT).show();
            }
        }, "android");
//        setWebViewClient(webViewClient);
//        setWebChromeClient(webChromeClient);

    }

    private void initSize(WebSettings settings) {
        int   screenDensity   = getResources().getDisplayMetrics(). densityDpi ;
        WebSettings.ZoomDensity   zoomDensity   = WebSettings.ZoomDensity. MEDIUM ;
        switch (screenDensity){
            case   DisplayMetrics.DENSITY_LOW :
                zoomDensity = WebSettings.ZoomDensity.CLOSE;
                break ;
            case   DisplayMetrics.DENSITY_MEDIUM :
                zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                break ;
            case   DisplayMetrics.DENSITY_HIGH :
                zoomDensity = WebSettings.ZoomDensity.FAR;
                break ;
        }
        settings.setDefaultZoom(zoomDensity) ;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                Log.e("按下了", "startY=" + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {// 确保startY有效
                    startY = (int) ev.getRawY();
                }

                int endY = (int) ev.getRawY();
                dy = endY - startY;
                dy = getNewDy(dy);
                if (dy > 0 && getScrollY() == 0) {
                    mWebViewLayout.setScrollTo(-dy);
//                    setPadding(0, dy, 0, 0);
                    Log.i("dy", "-->" + dy);
                    //  MotionEvent event = MotionEvent.obtain(0, 0, MotionEvent.ACTION_CANCEL, 0, 0, 0);
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(ev);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                Log.e("松手了", "Y的位置" + ev.getRawY());
//                setPadding(0, 0, 0, 0);
                mWebViewLayout.smoothScrollTo(0, 300, 0, new WebViewLayout.OnSmoothScrollFinishedListener() {
                    @Override
                    public void onSmoothScrollFinished() {
                    }
                });
//                mWebViewLayout.scrollTo(0, 0);

                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e("取消了", "");
                break;

        }
        return super.onTouchEvent(ev);

    }

    private int getNewDy(float dy) {
        return (int) (0.25 * dy * (2.0 - dy / getHeight()));
    }
    //TODO WebView的完善。。


    @Override
    public void loadUrl(String url) {
        if (mUrl == null || !mUrl.equals(url)) {
            mUrl = url;
        }
        if (!TextUtils.isEmpty(url) && url.contains("xxx")) {
            setCookie(url);
        }

        mNetworkInfo = connectionManager.getActiveNetworkInfo();
        super.loadUrl(mNetworkInfo == null ? "file:///android_asset/html/nowifi.html" : url);
    }

    /**
     * 设置是否支持js，默认开启的！！
     *
     * @param enable
     */
    public void setJsEnable(boolean enable) {
        getSettings().setJavaScriptEnabled(enable);
    }

    @SuppressLint("NewApi")
    private void setCookie(String url) {
        URI uri = URI.create(url);

        if (TextUtils.isEmpty(uri.getHost())) {
            return;
        }

        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(getContext());
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

    /**
     * 构建Webview公共Cookies
     *
     * @return
     */
    private List<String> getCookies() {
        Application app = null;
        List<String> cookies = new ArrayList<String>();
//        if (getContext().getApplicationContext() instanceof Application) {
//            app = (App) mContext.getApplicationContext();
//            AnanzuUserInfo user = app.getmUserinfo();
//            AnanzuCityInfo city = app.getmCityInfo();
//            if (user.getiUserID() > 0 && !TextUtils.isEmpty(user.getsToken())) {
//                cookies.add("ananzu_token=" + app.getmUserinfo().getsToken());
//                cookies.add("ananzu_islogintoken=1");
//                cookies.add("ananzu_currentmode=" + app.getCommInfo().getiCurrentStatus());
//            } else {
//                cookies.add("ananzu_islogintoken=0");
//            }
//
//            if (city != null) {
//                cookies.add("ananzu_cityid=" + city.getiCityId());
//                cookies.add("ananzu_cityname=" + city.getsName());
//            }
//        }

        return cookies;
    }

    public interface CallBack {
        @JavascriptInterface
        void goBack();
    }
}
