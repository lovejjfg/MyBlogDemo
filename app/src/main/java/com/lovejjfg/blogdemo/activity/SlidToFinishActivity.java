package com.lovejjfg.blogdemo.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.lovejjfg.blogdemo.base.BaseSlideFinishActivity;
import com.lovejjfg.blogdemo.R;

/**
 * Created by lovejjfg on 2015/11/8.
 */
public class SlidToFinishActivity extends BaseSlideFinishActivity {

    @Override
    public View initView(Bundle savedInstanceState) {
        return LayoutInflater.from(this).inflate(R.layout.activity_slide_finish, null);
    }


    @Override
    protected void performViewClick(View v) {

    }
}
