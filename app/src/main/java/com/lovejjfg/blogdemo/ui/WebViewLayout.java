package com.lovejjfg.blogdemo.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.webkit.WebSettings;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2015/11/8.
 */
public class WebViewLayout extends FrameLayout {

    private TopSlidWebView mWebView;
    private DecelerateInterpolator mScrollAnimationInterpolator;
    private SmoothScrollRunnable mCurrentSmoothScrollRunnable;
//    private FrameLayout mFrameLayout;

    public WebViewLayout(Context context) {
        this(context, null);
    }

    public WebViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {

        mWebView = new TopSlidWebView(context);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        addView(mWebView, 0, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }


    public TopSlidWebView getTopSlidWebView() {
        return mWebView;
    }

    protected void smoothScrollTo(int newScrollValue, long duration, long delayMillis,
                                  OnSmoothScrollFinishedListener listener) {
        if (null != mCurrentSmoothScrollRunnable) {
            mCurrentSmoothScrollRunnable.stop();
        }

        int oldScrollValue = getScrollY();

        if (oldScrollValue != newScrollValue) {
            if (null == mScrollAnimationInterpolator) {
                // Default interpolator is a Decelerate Interpolator
                mScrollAnimationInterpolator = new DecelerateInterpolator();
            }
            mCurrentSmoothScrollRunnable = new SmoothScrollRunnable(oldScrollValue, newScrollValue, duration, listener);

            if (delayMillis > 0) {
                postDelayed(mCurrentSmoothScrollRunnable, delayMillis);
            } else {
                post(mCurrentSmoothScrollRunnable);
            }
        }
    }

    public void setScrollTo(int dy) {
        this.scrollTo(0, dy);
    }

    public View getTopMsgView() {
        return null;
    }

    final class SmoothScrollRunnable implements Runnable {
        private final Interpolator mInterpolator;
        private final int mScrollToY;
        private final int mScrollFromY;
        private final long mDuration;
        private OnSmoothScrollFinishedListener mListener;

        private boolean mContinueRunning = true;
        private long mStartTime = -1;
        private int mCurrentY = -1;

        public SmoothScrollRunnable(int fromY, int toY, long duration, OnSmoothScrollFinishedListener listener) {
            mScrollFromY = fromY;
            mScrollToY = toY;
            mInterpolator = mScrollAnimationInterpolator;
            mDuration = duration;
            mListener = listener;
        }

        @Override
        public void run() {

            /**
             * Only set mStartTime if this is the first time we're starting,
             * else actually calculate the Y delta
             */
            if (mStartTime == -1) {
                mStartTime = System.currentTimeMillis();
            } else {

                /**
                 * We do do all calculations in long to reduce software float
                 * calculations. We use 1000 as it gives us good accuracy and
                 * small rounding errors
                 */
                long normalizedTime = (1000 * (System.currentTimeMillis() - mStartTime)) / mDuration;
                normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);

                final int deltaY = Math.round((mScrollFromY - mScrollToY)
                        * mInterpolator.getInterpolation(normalizedTime / 1000f));
                mCurrentY = mScrollFromY - deltaY;
                WebViewLayout.this.scrollTo(0, mCurrentY);
            }

            // If we're not at the target Y, keep going...
            if (mContinueRunning && mScrollToY != mCurrentY) {
                ViewCompat.postOnAnimation(WebViewLayout.this, this);
            } else {
                if (null != mListener) {
                    mListener.onSmoothScrollFinished();
                }
            }
        }

        public void stop() {
            mContinueRunning = false;
            removeCallbacks(this);
        }
    }

    interface OnSmoothScrollFinishedListener {
        void onSmoothScrollFinished();
    }

//
}
