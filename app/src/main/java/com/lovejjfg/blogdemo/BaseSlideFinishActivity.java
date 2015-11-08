package com.lovejjfg.blogdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseSlideFinishActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(initView(savedInstanceState));
    }


    @Override
    public void onClick(View v) {
        performViewClick(v);
    }

    public abstract View initView(Bundle savedInstanceState);

    protected abstract void performViewClick(View v);

    public void finishSelf() {
        this.finish();
        this.overridePendingTransition(0, 0);//取消Activity的动画。
    }
}
