package com.lovejjfg.blogdemo.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.lovejjfg.blogdemo.R;


public class CustomerProgress extends View implements View.OnClickListener {

    private static final Interpolator ANGLE_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator SWEEP_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final int ANGLE_ANIMATOR_DURATION = 1500;//转速
    private static final int SWEEP_ANIMATOR_DURATION = 1300;
    private static final int MIN_SWEEP_ANGLE = 30;
    private static final int DEFAULT_BORDER_WIDTH = 3;
    private final RectF fBounds = new RectF();

    private ObjectAnimator mObjectAnimatorSweep;
    private ObjectAnimator mObjectAnimatorAngle;
    private boolean mModeAppearing = true;
    private Paint mPaint;
    private float mCurrentGlobalAngleOffset;
    private float mCurrentGlobalAngle;
    private float mCurrentSweepAngle;
    private float mBorderWidth;
    private boolean mRunning;
    private int[] mColors;
    private int mCurrentColorIndex;
    private int mNextColorIndex;
    private static final int STATE_LOADING = 1;
    private static final int STATE_DONE = 2;
    private int mCurrentState;
    private Path mHook;
    private Paint mHookPaint;
    private ValueAnimator hookAnimator;
    private Path mArrow;
    private float mRingCenterRadius;
    private static int ARROW_WIDTH = 10;
    private static int ARROW_HEIGHT = 5;
    private Paint mArrowPaint;
    private float mArrowScale = 1f;
    private float mStrokeInset = 2.5f;
    private static final float ARROW_OFFSET_ANGLE = 5;


    public CustomerProgress(Context context) {
        this(context, null);
    }

    public CustomerProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        float density = context.getResources().getDisplayMetrics().density;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomerProgress, defStyleAttr, 0);
        mBorderWidth = a.getDimension(R.styleable.CustomerProgress_progressdialogborderWidth,
                DEFAULT_BORDER_WIDTH * density);
        ARROW_WIDTH = (int) (mBorderWidth);
        ARROW_HEIGHT = (int) ((int) mBorderWidth * 0.5f);
        a.recycle();
        mColors = new int[4];
        mColors[0] = Color.RED;//context.getResources().getColor(R.color.white);
        mColors[1] = Color.BLUE;//context.getResources().getColor(R.color.white);
        mColors[2] = Color.GREEN;//context.getResources().getColor(R.color.white);
        mColors[3] = Color.GRAY;//context.getResources().getColor(R.color.white);
        mCurrentColorIndex = 0;
        mNextColorIndex = 1;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Cap.ROUND);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mColors[mCurrentColorIndex]);

        mHookPaint = new Paint(mPaint);
        mArrowPaint = new Paint(mPaint);

        mHook = new Path();

        setupAnimations();
    }

    private void start() {
        if (isRunning()) {
            return;
        }
        mRunning = true;
        mCurrentState = STATE_LOADING;
        mObjectAnimatorAngle.start();
        mObjectAnimatorSweep.start();
        postDelayed(new Runnable() {
            @Override
            public void run() {
//                finish();
            }
        }, 5000);
        setOnClickListener(this);
        invalidate();
    }

    public void finish() {
        mRunning = false;
        mObjectAnimatorAngle.cancel();
        mObjectAnimatorSweep.cancel();
        mCurrentState = STATE_DONE;
        if (!hookAnimator.isRunning()) {
            hookAnimator.start();
        }
    }

    private void stop() {
        if (!isRunning()) {
            return;
        }
        mRunning = false;
        mObjectAnimatorAngle.cancel();
        mObjectAnimatorSweep.cancel();
        invalidate();
    }

    private boolean isRunning() {
        return mRunning;
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            start();
        } else {
            stop();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        start();
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        fBounds.left = mBorderWidth * 2 ;
        fBounds.right = w - mBorderWidth * 2 ;
        fBounds.top = mBorderWidth * 2 ;
        fBounds.bottom = h - mBorderWidth * 2 ;

        mRingCenterRadius = (Math.min(fBounds.right - fBounds.centerX(), fBounds.centerY() - fBounds.top))-mBorderWidth*0.5f;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        switch (mCurrentState) {
            case STATE_LOADING:
                drawArc(canvas);
                break;
            case STATE_DONE:
                drawHook(canvas);
                break;
        }

    }

    private void drawHook(Canvas canvas) {
        mHook.reset();
        mHook.moveTo(fBounds.centerX() - fBounds.width() * 0.25f, fBounds.centerY());
        mHook.lineTo(fBounds.centerX() - fBounds.width() * 0.1f, fBounds.centerY() + fBounds.height() * 0.18f);
        mHook.lineTo(fBounds.centerX() + fBounds.width() * 0.3f, fBounds.centerY() - fBounds.height() * 0.2f);
        canvas.drawPath(mHook, mHookPaint);
        canvas.drawArc(fBounds, 0, 360, false, mHookPaint);

    }

    private void drawArc(Canvas canvas) {
        float startAngle = mCurrentGlobalAngle - mCurrentGlobalAngleOffset;
        float sweepAngle = mCurrentSweepAngle;
        if (mModeAppearing) {
            mPaint.setColor(gradient(mColors[mCurrentColorIndex], mColors[mNextColorIndex],
                    mCurrentSweepAngle / (360 - MIN_SWEEP_ANGLE * 2)));
            sweepAngle += MIN_SWEEP_ANGLE;
        } else {
            startAngle = startAngle + sweepAngle;
            sweepAngle = 360 - sweepAngle - MIN_SWEEP_ANGLE;
        }
        canvas.drawArc(fBounds, startAngle, sweepAngle, false, mPaint);
        drawTriangle(canvas, startAngle, sweepAngle);
    }

    public void drawTriangle(Canvas c, float startAngle, float sweepAngle) {
        if (mArrow == null) {
            mArrow = new Path();
            mArrow.setFillType(android.graphics.Path.FillType.EVEN_ODD);
        } else {
            mArrow.reset();
        }

        // Adjust the position of the triangle so that it is inset as
        // much as the arc, but also centered on the arc.
        float inset = (int) mStrokeInset / 2 * mArrowScale;
        float x = (float) (mRingCenterRadius * Math.cos(0) + fBounds.centerX());
        float y = (float) (mRingCenterRadius * Math.sin(0) + fBounds.centerY());

        // Update the path each time. This works around an issue in SKIA
        // where concatenating a rotation matrix to a scale matrix
        // ignored a starting negative rotation. This appears to have
        // been fixed as of API 21.
        mArrow.moveTo(0, 0);
        mArrow.lineTo(ARROW_WIDTH * mArrowScale, 0);
        mArrow.lineTo((ARROW_WIDTH * mArrowScale / 2), (ARROW_HEIGHT
                * mArrowScale));
        mArrow.offset(x, y);
        mArrow.close();
        // draw a triangle
        mArrowPaint.setColor(Color.RED);
        mArrowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        c.rotate(startAngle + sweepAngle, fBounds.centerX(),
                fBounds.centerY());
        c.drawPath(mArrow, mPaint);
//        c.drawPoint(x,y,mPaint);
    }

    private static int gradient(int color1, int color2, float p) {
        int r1 = (color1 & 0xff0000) >> 16;
        int g1 = (color1 & 0xff00) >> 8;
        int b1 = color1 & 0xff;
        int r2 = (color2 & 0xff0000) >> 16;
        int g2 = (color2 & 0xff00) >> 8;
        int b2 = color2 & 0xff;
        int newr = (int) (r2 * p + r1 * (1 - p));
        int newg = (int) (g2 * p + g1 * (1 - p));
        int newb = (int) (b2 * p + b1 * (1 - p));
        return Color.argb(255, newr, newg, newb);
    }

    private void toggleAppearingMode() {
        mModeAppearing = !mModeAppearing;
        if (mModeAppearing) {
            mCurrentColorIndex = ++mCurrentColorIndex % 4;
            mNextColorIndex = ++mNextColorIndex % 4;
            mCurrentGlobalAngleOffset = (mCurrentGlobalAngleOffset + MIN_SWEEP_ANGLE * 2) % 360;
        }
    }
    // ////////////////////////////////////////////////////////////////////////////
    // ////////////// Animation

    private Property<CustomerProgress, Float> mAngleProperty = new Property<CustomerProgress, Float>(Float.class, "angle") {
        @Override
        public Float get(CustomerProgress object) {
            return object.getCurrentGlobalAngle();
        }

        @Override
        public void set(CustomerProgress object, Float value) {
            object.setCurrentGlobalAngle(value);
        }
    };

    private Property<CustomerProgress, Float> mSweepProperty = new Property<CustomerProgress, Float>(Float.class, "arc") {
        @Override
        public Float get(CustomerProgress object) {
            return object.getCurrentSweepAngle();
        }

        @Override
        public void set(CustomerProgress object, Float value) {
            object.setCurrentSweepAngle(value);
        }
    };

    private void setupAnimations() {
        mObjectAnimatorAngle = ObjectAnimator.ofFloat(this, mAngleProperty, 360f);
        mObjectAnimatorAngle.setInterpolator(ANGLE_INTERPOLATOR);
        mObjectAnimatorAngle.setDuration(ANGLE_ANIMATOR_DURATION);
        mObjectAnimatorAngle.setRepeatMode(ValueAnimator.RESTART);
        mObjectAnimatorAngle.setRepeatCount(ValueAnimator.INFINITE);

        mObjectAnimatorSweep = ObjectAnimator.ofFloat(this, mSweepProperty, 360f - MIN_SWEEP_ANGLE * 2);
        mObjectAnimatorSweep.setInterpolator(SWEEP_INTERPOLATOR);
        mObjectAnimatorSweep.setDuration(SWEEP_ANIMATOR_DURATION);
        mObjectAnimatorSweep.setRepeatMode(ValueAnimator.RESTART);
        mObjectAnimatorSweep.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimatorSweep.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                toggleAppearingMode();
            }
        });

        hookAnimator = ValueAnimator.ofInt(0, 255);
        hookAnimator.setInterpolator(ANGLE_INTERPOLATOR);
        hookAnimator.setDuration(500);
        hookAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mHookPaint.setAlpha((Integer) animation.getAnimatedValue());
                invalidate();
            }
        });
    }

    public void setCurrentGlobalAngle(float currentGlobalAngle) {
        mCurrentGlobalAngle = currentGlobalAngle;
        invalidate();
    }

    public float getCurrentGlobalAngle() {
        return mCurrentGlobalAngle;
    }

    public void setCurrentSweepAngle(float currentSweepAngle) {
        mCurrentSweepAngle = currentSweepAngle;
        invalidate();
    }

    public float getCurrentSweepAngle() {
        return mCurrentSweepAngle;
    }

    @Override
    public void onClick(View v) {
        start();
    }
}
