package com.lovejjfg.blogdemo.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Joe on 2016-07-05
 * Email: zhangjun166@pingan.com.cn
 */
public class ScrollAbleViewPager extends ViewPager {
    private boolean scrollble;

    public ScrollAbleViewPager(Context context) {
        super(context);
    }

    public ScrollAbleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("ScrollAbleViewPager", "onInterceptTouchEvent: "+scrollble);
        return  scrollble && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !scrollble || super.onTouchEvent(ev);
    }


    public boolean isScrollble() {
        return scrollble;
    }
    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }
}

