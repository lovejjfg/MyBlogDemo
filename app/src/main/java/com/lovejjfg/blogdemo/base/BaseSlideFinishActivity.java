package com.lovejjfg.blogdemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.utils.BaseUtil;

public abstract class BaseSlideFinishActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolBar;
    private TextView mTittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);

        setContentView(initView(savedInstanceState));//(frameLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        frameLayout.addView(initView(savedInstanceState));
//        initToolbar();
        init();
    }

    private void initToolbar() {
        View appBar = LayoutInflater.from(this).inflate(R.layout.app_bar, null);
        mToolBar = (Toolbar) appBar.findViewById(R.id.app_bar);
        mTittle = (TextView) appBar.findViewById(R.id.app_tittle);
        mTittle.setText("hahaahah");
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();//.setDisplayHomeAsUpEnabled(true);
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            this.overridePendingTransition(0, R.anim.finish_activity_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void init() {
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

    public void startActivityOnspecifiedAnimation(Activity context, Class<?> cls) {
        BaseUtil.startActivityOnspecifiedAnimation(context, cls);
    }
}
