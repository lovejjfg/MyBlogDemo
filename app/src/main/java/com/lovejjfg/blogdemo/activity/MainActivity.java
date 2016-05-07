package com.lovejjfg.blogdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.utils.BaseUtil;

import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_slide).setOnClickListener(this);
        findViewById(R.id.tv_browser).setOnClickListener(this);
        findViewById(R.id.tv_browser2).setOnClickListener(this);
        findViewById(R.id.scrollView).setOnClickListener(this);
        findViewById(R.id.bottom_sheet).setOnClickListener(this);
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
//                BaseUtil.startActivityOnspecifiedAnimation(this, BrowserActivity.class);
                break;
            case R.id.tv_browser2:
                BaseUtil.startActivityOnspecifiedAnimation(this, BrowserActivity2.class);
                break;
            case R.id.scrollView:

                BaseUtil.startActivityOnspecifiedAnimation(this, FangshiActivity.class);
                break;
            case R.id.bottom_sheet:

                BaseUtil.startActivityOnspecifiedAnimation(this, BottomSheetActivity.class);
                break;
        }
    }
}
