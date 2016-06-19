package com.lovejjfg.blogdemo.activity;

import android.animation.Animator;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.ui.AnimUtils;
import com.lovejjfg.blogdemo.utils.BaseUtil;
import com.lovejjfg.blogdemo.utils.JumpUtil;
import com.lovejjfg.blogdemo.utils.glide.RoundTransform;

import java.util.Set;

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
    @Bind(R.id.iv)
    ImageView mIv;
    int minOffset = 0;
    private int offset;
    private Interpolator interpolator;
    private int btHeight;
    private boolean isShown = false;
    private int currentDy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mTv2.bringToFront();
        mBt.setOnClickListener(this);
        Glide.with(this)
                .load(R.mipmap.girl2)
                .listener(new RequestListener<Integer, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .transform(new RoundTransform(getApplicationContext(), 100))
                .into(mIv);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            // TODO: 2016/5/25 滑动相关的逻辑

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int dy = scrollY - oldScrollY;
                Log.e(TAG, "onScrollChange: " + scrollY);
                if (dy > 5) {
                    buttonOutAnimation(interpolator);
                } else if (dy < -5) {
                    buttonEnterAnimation(interpolator);
                } else {
                    setBtOffset(mBt, dy, btHeight);
                }
                setOffset(mTv2, scrollY, minOffset);
            }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {

            private boolean flag;
            private int startY;

            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (currentDy == 0 || currentDy == btHeight) {
                            break;
                        }
                        if (currentDy >= btHeight * 0.4) {
                            isShown = true;
                            buttonOutAnimation(interpolator);
                        } else {
                            isShown = false;
                            buttonEnterAnimation(interpolator);
                        }
                        break;

                }
                return false;
            }
        });

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

    private void setBtOffset(Button mBt, int dy, int btHeight) {
        currentDy = (int) (mBt.getTranslationY() + dy);
        if (currentDy < 0) {
            currentDy = 0;
            isShown = true;
        } else if (currentDy > btHeight) {
            currentDy = btHeight;
            isShown = false;
        }
        if (mBt.getTranslationY() != currentDy) {
            mBt.setTranslationY(currentDy);
            ViewCompat.postInvalidateOnAnimation(mBt);
        }


    }

    public void setOffset(View view, int offset, int minOffset) {

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
        if (isShown) {
            return;
        }
        btHeight = mBt.getMeasuredHeight();
        mBt.setTranslationY(currentDy == 0 ? btHeight : currentDy);
        mBt.setAlpha(0.5f);
        mBt.animate()
                .translationY(0)
                .alpha(1f)
                .setDuration(200)
                .setInterpolator(interp)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        isShown = true;
                        Log.i(TAG, "onEnterAnimationStart: " + isShown);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        currentDy = 0;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    private void buttonOutAnimation(Interpolator interp) {
        if (!isShown) {
            return;
        }
        mBt.setTranslationY(currentDy == 0 ? 0 : currentDy);
        mBt.animate()
                .translationY(btHeight)
                .setDuration(200)
                .setInterpolator(interp)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        isShown = false;
                        Log.i(TAG, "onOutAnimationStart: " + isShown);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        currentDy = btHeight;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    private void viewEnterAnimation(View view, float offset, Interpolator interp) {
        view.setTranslationY(offset);
        view.animate()
                .translationY(0f)
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

//                BaseUtil.startActivityOnspecifiedAnimation(this, BottomSheetActivity.class);
                JumpUtil.jumpToBottome(this);
                break;
            case R.id.button:
                buttonOutAnimation(interpolator);
                break;
        }
    }
}
