package com.lovejjfg.circlepointdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by lovejjfg@163.com on 2015-11-12.
 */
public class SlidingCircleLayout extends FrameLayout {
    private LinearLayout mLinearLayout;
    private View red_point;
    private int i;
    //    private int mLayoutWidth;
//    private int mLayoutHeight;
    private int mleftMargin;
    private Drawable point_selected;
    private Drawable point_default;
    private boolean mSmoothSlide;
    private int mSelectedDiameter;
    private int mDefaultDiameter;
    private float mRadius;

    public SlidingCircleLayout(Context context) {
        this(context, null);
    }

    public SlidingCircleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingCircleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.SlidingCircleLayout);
        mDefaultDiameter = a.getDimensionPixelOffset(R.styleable.SlidingCircleLayout_point_default_diameter, dip2px(context, 10));
        mSelectedDiameter = a.getDimensionPixelOffset(R.styleable.SlidingCircleLayout_point_selected_diameter, dip2px(context, 10));

        mRadius = Math.abs((float) (mDefaultDiameter - mSelectedDiameter)) / 2;
        mleftMargin = a.getDimensionPixelOffset(R.styleable.SlidingCircleLayout_point_margin, dip2px(context, 5));
        mSmoothSlide = a.getBoolean(R.styleable.SlidingCircleLayout_point_smooth_slide, true);
        try {
            point_selected = a.getDrawable(R.styleable.SlidingCircleLayout_point_selected);
            point_default = a.getDrawable(R.styleable.SlidingCircleLayout_point_default);
        } catch (Exception e) {
            throw new IllegalArgumentException("必须使用color和drawble的属性");
        }

        a.recycle();
        init(context);

    }

    private void init(Context context) {
        mLinearLayout = new LinearLayout(context);

        addView(mLinearLayout, 0, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        red_point = new View(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mSelectedDiameter, mSelectedDiameter);
//        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        if (point_selected != null) {
            red_point.setBackgroundDrawable(point_selected);
        } else {
            red_point.setBackgroundResource(R.drawable.point);
        }
        if (mRadius > 0) {
            layoutParams.setMargins((int) (mRadius), (int) (mRadius), 0, 0);
        }
//        red_point.setBackgroundResource(point_selected == null ? R.drawable.point_red:point_selected);

        addView(red_point, 1, layoutParams);


    }


    private void setPointCount(int count) {
        if (count == 0) {
            throw new IllegalStateException("填充viewpager的数量应该大于0");
        }

        for (int i = 0; i < count; i++) {
            /**
             * 设置圆点
             */
            LinearLayout.LayoutParams pLayoutParams = new LinearLayout.LayoutParams(mDefaultDiameter, mDefaultDiameter);
            View p = new View(getContext());
            p.setLayoutParams(pLayoutParams);
            if (point_default != null) {
                p.setBackgroundDrawable(point_default);
            } else {
                p.setBackgroundResource(R.drawable.point_red);
            }

            if (i > 0) {
                pLayoutParams.leftMargin = mleftMargin;
            }
            mLinearLayout.addView(p, i);
        }
        if (count >= 2) {
            mLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                /**
                 * 当布局测量好之后，来获取到点与点之间的左边距
                 */
                @Override
                public void onGlobalLayout() {
                    i = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
                    mLinearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }
    }


    /**
     * 请在ViewPager设置Adapter之后调用该方法。
     *
     * @param viewPager viewpager
     */
    public void addViewPager(ViewPager viewPager) {
        if (viewPager.getAdapter() == null) {
            throw new IllegalArgumentException("you should call ViewPager.setAdapter() first!!!");
        }
        //添加底部圆圈个数
        setPointCount(viewPager.getAdapter().getCount());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //百分比：positionOffset
                if (mSmoothSlide) {
                    float x = (i * positionOffset);
                    updatePoint(position, x);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (!mSmoothSlide) {
                    updatePoint(position, 0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void updatePoint(int position, float x) {
        LayoutParams params = (LayoutParams) red_point.getLayoutParams();
        if (mRadius > 0) {
            params.setMargins((int) (mRadius + x + position * i), (int) (mRadius), 0, 0);
        } else {
            params.leftMargin = (int) (mRadius + x + position * i);
        }
//
        red_point.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //TODO 解决用户不写wrap_content
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight() - getPaddingLeft();
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
//
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(sizeWidth, modeWidth == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeWidth);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(sizeHeight, modeHeight == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeHeight);
        super.onMeasure(childWidthMeasureSpec, childHeightMeasureSpec);

    }


    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
