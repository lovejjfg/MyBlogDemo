/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lovejjfg.blogdemo.ui;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class HeadViewFrameLayout1 extends FrameLayout implements NestedScrollingParent, NestedScrollingChild, TouchCircleView.OnLoadingListener {
    private String TAG = HeadViewFrameLayout1.class.getSimpleName();
    // configurable attribs

    // state
    private float totalDrag;
    private TouchCircleView header;
    private float defaulTranslationY;
    FastOutLinearInInterpolator fastOutLinearInInterpolator = new FastOutLinearInInterpolator();
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    private View targetView;
    private boolean mRefresh;


    public HeadViewFrameLayout1(Context context) {
        this(context, null, 0);
    }

    public HeadViewFrameLayout1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadViewFrameLayout1(Context context, AttributeSet attrs,
                                int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        header = new TouchCircleView(getContext());
        header.setListener(this);
        float density = context.getResources().getDisplayMetrics().density;
        addView(header, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (density * 120)));
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        // Reset the counter of how much leftover scroll needs to be consumed.
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
        // Dispatch up to the nested parent
        startNestedScroll(axes & ViewCompat.SCROLL_AXIS_VERTICAL);
    }


    // NestedScrollingChild

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX,
                                    float velocityY) {
        return dispatchNestedPreFling(velocityX, velocityY);
    }


    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }


    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & 2) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // if we're in a drag gesture and the user reverses up the we should take those events
        Log.e(TAG, "onNestedPreScroll: " + dy);
        if (!header.ismRunning() && dy > 0 && totalDrag > defaulTranslationY) {
            updateOffset(dy);
            consumed[1] = dy;
        }
    }


    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        Log.e(TAG, "onNestedScroll: " + dyUnconsumed);
        if (!header.ismRunning() && dyUnconsumed < 0) {
            updateOffset(dyUnconsumed);
        }

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (!header.ismRunning() && !consumed) {
            updateOffset((int) velocityY);
            return true;
        }
        return false;
    }


    private void updateOffset(int dyUnconsumed) {

        totalDrag -= dyUnconsumed * 0.35;
        Log.i(TAG, "updateOffset: " + totalDrag);
        if (totalDrag < 0) {
            totalDrag = 0;
        }
        targetView.setTranslationY(totalDrag);
        if (!header.ismRunning()) {
            header.handleOffset((int) (totalDrag * 1.2));
        }
//        if (header.getTranslationY() == 0 && !isStart) {
//            isStart = true;
//            header.start();
//        }
    }

    @Override
    public void onStopNestedScroll(View child) {
        mNestedScrollingParentHelper.onStopNestedScroll(child);
        Log.i(TAG, "onStopNestedScroll: ");
        header.resetTouch();
    }

    private void resetDrag(int offset) {
        targetView.animate()
                .translationY(offset)
                .setDuration(200)
                .setInterpolator(fastOutLinearInInterpolator)
                .start();
        totalDrag = defaulTranslationY;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // TODO: 2016-06-29 使用接口定义相关
        targetView = getChildAt(1);
        defaulTranslationY = targetView.getTranslationY();
        totalDrag = defaulTranslationY;
    }

    @Override
    public void onProgressStateChange(int state, boolean isHide) {
        if (isHide && !header.ismRunning()) {
            resetDrag(0);
        }
    }

    @Override
    public void onProgressLoading() {
        resetDrag((int) (header.getHeight() * 0.6f));
        // TODO: 2016-07-18 开始加载一些东西。。。
    }


    public void setRefresh(boolean refresh) {
        if (mRefresh == refresh) {
            return;
        }
        mRefresh = refresh;
        header.setRefresh(mRefresh);
    }

    public void setRefreshError() {
        header.setCurrentState(TouchCircleView.STATE_DRAW_ERROR);
    }

    public void setRefreshSuccess() {
        header.setCurrentState(TouchCircleView.STATE_DRAW_SUCCESS);
    }
}
