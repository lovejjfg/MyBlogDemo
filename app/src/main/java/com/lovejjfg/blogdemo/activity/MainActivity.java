package com.lovejjfg.blogdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.ui.AnimUtils;
import com.lovejjfg.blogdemo.utils.BaseUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.container)
    NestedScrollView scrollView;
    @Bind(R.id.tv_browser2)
    TextView mTv2;
    @Bind(R.id.tv_browser)
    TextView mTv1;
    @Bind(R.id.tv_slide)
    TextView mTvSlide;
    @Bind(R.id.scrollView)
    TextView mTvScroll;
    @Bind(R.id.bottom_sheet)
    Button mBtSheet;
    @Bind(R.id.button)
    Button mBt;
    int minOffset = 0;
    private int offset;
    private Interpolator interpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mTv2.bringToFront();
        mBt.setOnClickListener(this);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            // TODO: 2016/5/25 滑动相关的逻辑
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e(TAG, "onScrollChange: " + scrollY);
                setOffset(mTv2, scrollY);
            }
        });

//        if (stick != null) {
//            stick.setTranslationY(100);
//        }
        mTvSlide.post(new Runnable() {


            @Override
            public void run() {
                interpolator = AnimUtils.getLinearOutSlowInInterpolator(MainActivity.this);
                offset = mTvSlide.getTop();
                minOffset = offset;
                Log.i("offset：", minOffset + "");
                mTv2.setTranslationY(minOffset);


                viewEnterAnimation(mTvSlide, offset, interpolator);
                buttonEnterAnimation(interpolator);
                offset *= 2;
                viewEnterAnimation(mTv1, offset, interpolator);

            }
        });

        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTvSlide.setOnClickListener(this);
        mTvScroll.setOnClickListener(this);
        mBtSheet.setOnClickListener(this);
    }

    public void setOffset(View view, int offset) {

        offset = Math.max(minOffset, offset);
        if (offset > 105) {
            view.refreshDrawableState();
        }
        if (view.getTranslationY() != offset) {
            view.setTranslationY(offset);
            ViewCompat.postInvalidateOnAnimation(view);
        }
    }
    private void buttonEnterAnimation(Interpolator interp) {
        int startY = mBt.getMeasuredHeight();
        Log.i(TAG, "buttonEnterAnimation: Bottom:" + mBt.getBottom() + "::height:" + mBt.getMeasuredHeight());
        mBt.setTranslationY(startY);
        mBt.setAlpha(0.5f);
        mBt.animate()
                .translationY(0)
                .alpha(1f)
                .setDuration(200)
                .setInterpolator(interp)
                .setListener(null)
                .start();
    }
    private void buttonOutAnimation(Interpolator interp) {
        int startY = mBt.getMeasuredHeight();
        Log.i(TAG, "buttonEnterAnimation: Bottom:" + mBt.getBottom() + "::height:" + mBt.getMeasuredHeight());
        mBt.setTranslationY(0);
        mBt.setAlpha(0.5f);
        mBt.animate()
                .translationY(startY)
                .alpha(1f)
                .setDuration(200)
                .setInterpolator(interp)
                .setListener(null)
                .start();
    }

    private void viewEnterAnimation(View view, float offset, Interpolator interp) {
        view.setTranslationY(offset);
        view.setAlpha(0.5f);
        view.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(300)
                .setInterpolator(interp)
                .setListener(null)
                .start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_slide:
                Blurry.with(this)
                        .color(Color.argb(66, 255, 255, 0))
                        .radius(25)
                        .sampling(2)
                        .async()
                        .onto((ViewGroup) getWindow().getDecorView());
//                BaseUtil.startActivityOnspecifiedAnimation(this, SlidToFinishActivity.class);

                break;
            case R.id.tv_browser:
                Blurry.delete((ViewGroup) getWindow().getDecorView());
                BaseUtil.startActivityOnspecifiedAnimation(this, ScrollingActivity.class);
//                BaseUtil.startActivityOnspecifiedAnimation(this, BrowserActivity.class);
                break;
            case R.id.tv_browser2:
                BaseUtil.startActivityOnspecifiedAnimation(this, ScrollingActivity.class);
                break;
            case R.id.scrollView:

                BaseUtil.startActivityOnspecifiedAnimation(this, FangshiActivity.class);
                break;
            case R.id.bottom_sheet:

                BaseUtil.startActivityOnspecifiedAnimation(this, BottomSheetActivity.class);
                break;
            case R.id.button:
                buttonOutAnimation(interpolator);
                break;
        }
    }
}
