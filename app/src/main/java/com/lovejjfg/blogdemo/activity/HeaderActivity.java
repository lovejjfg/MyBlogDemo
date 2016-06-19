package com.lovejjfg.blogdemo.activity;

import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lovejjfg.blogdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HeaderActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.iv)
    ImageView mIv;
    @Bind(R.id.bt)
    Button mBt;
    @Bind(R.id.bt2)
    Button mBt2;
//    @Bind(R.id.nest_scroll)
//    NestedScrollView mContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
        ButterKnife.bind(this);

        mIv.animate()
                .rotation(0)
                .translationY(0)
                .scaleY(1)
                .scaleX(1)
                .alpha(1)
                .setDuration(2000)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();
        mBt.animate()
                .scaleX(1)
                .alpha(1)
                .setDuration(2000)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();
        mBt2.animate()
                .scaleX(1)
                .alpha(1)
                .setDuration(2000)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();

//        mContainer.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
////                mIv.setTranslationY(scrollY);
////                Log.i("TAG", "onScrollChange: " + v.getChildAt(0));
//                View view = v.getChildAt(0);
//                Log.i("TAG", "onScrollChange: " + v.getScrollY());
//                Log.i("TAG", "onScrollChange: " + (mBt.getTop()));
////                if (view instanceof LinearLayout) {
////                    if (((LinearLayout) view).getChildAt(0).getId() == mBt.getId()) {
////                        Log.e("TAG", "onScrollChange: " + v.getChildAt(0));
////                        mBt.setTranslationY(scrollY);
////                    }
////                }
//                if (scrollY >= (mBt.getTop())) {
//                    mBt.setTranslationY(scrollY - mBt.getTop());
//                } else {
//                    mBt.setTranslationY(0);
//                }
//
//                if (mBt2.getTop() <= scrollY) {
//                    mBt2.setTranslationY(scrollY - mBt2.getTop());
//
//                } else {
//                    mBt2.setTranslationY(0);
//                }
//
//            }
//        });

        mIv.setOnClickListener(this);
        mBt.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Log.i("TAG", "onClick: " + v.getId());
    }
}
