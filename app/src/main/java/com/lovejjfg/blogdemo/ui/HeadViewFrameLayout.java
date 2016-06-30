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
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;


public class HeadViewFrameLayout extends FrameLayout {
    private String TAG = HeadViewFrameLayout.class.getSimpleName();
    // configurable attribs

    // state
    private float totalDrag;
    private PathTextView header;
    private float defaulTranslationY;
    private boolean isStart;


    public HeadViewFrameLayout(Context context) {
        this(context, null, 0);
    }

    public HeadViewFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadViewFrameLayout(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & 2) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // if we're in a drag gesture and the user reverses up the we should take those events
        Log.e(TAG, "onNestedPreScroll: " + dy);
        if (dy >= 0 && totalDrag >= defaulTranslationY) {
            updateOffset(dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(target, velocityX, velocityY);
    }


    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        Log.i(TAG, "onNestedScroll: " + dyUnconsumed);
        updateOffset(dyUnconsumed);

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (!consumed) {
            updateOffset((int) velocityY);
            return true;
        }
        return false;
    }

    private void updateOffset(int dyUnconsumed) {
        totalDrag -= dyUnconsumed;
        Log.i(TAG, "updateOffset: " + totalDrag);
        header.setTranslationY(totalDrag > 0 ? 0 : totalDrag);
        if (header.getTranslationY() == 0 && !isStart) {
            isStart = true;
            header.start();
        }
    }

    @Override
    public void onStopNestedScroll(View child) {
        Log.i(TAG, "onStopNestedScroll: ");
        resetDrag();
    }

    private void resetDrag() {
        header.animate().translationY(defaulTranslationY)
                .setDuration(500)
                .setInterpolator(AnimationUtils.loadInterpolator(getContext(), android.R
                        .interpolator.fast_out_slow_in))
                .start();
        totalDrag = defaulTranslationY;
        isStart = false;
        header.end();
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
        if (getChildAt(1) instanceof PathTextView) {
            header = (PathTextView) getChildAt(1);
        }
        defaulTranslationY = header.getTranslationY();
        totalDrag = defaulTranslationY;
    }
}
