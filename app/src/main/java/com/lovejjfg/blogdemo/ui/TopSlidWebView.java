package com.lovejjfg.blogdemo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/11/8.
 */
public class TopSlidWebView extends WebView {

    private int startY;
    private View topMsgView;
    private int dy;
    private WebViewLayout mWebViewLayout;

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

    private void init(Context context) {
        post(new Runnable() {
            @Override
            public void run() {
                mWebViewLayout = (WebViewLayout) getParent();
//                topMsgView = mWebViewLayout.getTopMsgView();
            }
        });
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
                       Toast.makeText(getContext(), "...", 0).show();
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

}
