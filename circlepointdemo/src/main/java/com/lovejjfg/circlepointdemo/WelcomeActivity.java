package com.lovejjfg.circlepointdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANGJUN166 on 2015-11-12.
 */
public class WelcomeActivity extends Activity implements View.OnClickListener {

    private ViewPager mVPager;
    private int[] guides;
    private List<ImageView> imageViews;
    private SlidingCircleLayout mCircleLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    public void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        mVPager = (ViewPager) findViewById(R.id.vp_pager);
        mCircleLayout = (SlidingCircleLayout) findViewById(R.id.scl);



    }

    public void initData() {
        guides = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};


        imageViews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(guides[i]);
            imageViews.add(imageView);
        }
        MyAdapter mAdapter = new MyAdapter();
        mVPager.setAdapter(mAdapter);
        mCircleLayout.addViewPager(mVPager);



    }

    public void initListener() {
        mVPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("onPageSelected", position + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void processClick(View v) {

    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return guides.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 这个方法是在初始化的时候才会被调用，一般会保留左右加当前的界面！
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
