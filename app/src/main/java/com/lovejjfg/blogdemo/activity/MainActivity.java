package com.lovejjfg.blogdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.utils.BaseUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_slide).setOnClickListener(this);
        findViewById(R.id.tv_browser).setOnClickListener(this);
        findViewById(R.id.tv_browser2).setOnClickListener(this);
        findViewById(R.id.scrollView).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_slide:
                BaseUtil.startActivityOnspecifiedAnimation(this, SlidToFinishActivity.class);
                break;
            case R.id.tv_browser:
                BaseUtil.startActivityOnspecifiedAnimation(this, BrowserActivity.class);
                break;
            case R.id.tv_browser2:
                BaseUtil.startActivityOnspecifiedAnimation(this, BrowserActivity2.class);
                break;
            case R.id.scrollView:

                BaseUtil.startActivityOnspecifiedAnimation(this, FangshiActivity.class);
                break;
        }
    }
}
