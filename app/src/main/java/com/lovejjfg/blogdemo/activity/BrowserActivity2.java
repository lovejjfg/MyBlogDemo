package com.lovejjfg.blogdemo.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.base.BaseSlideFinishActivity;

/**
 * Created by 张俊 on 2016/1/17.
 */
public class BrowserActivity2 extends BaseSlideFinishActivity {
    @Override
    public View initView(Bundle savedInstanceState) {
        View v = LayoutInflater.from(this).inflate(R.layout.activity_browser2, null);

        final WebView mWeb = (WebView) v.findViewById(R.id.web);
        mWeb.post(new Runnable() {
            @Override
            public void run() {
                mWeb.loadUrl("http://www.devtf.cn/?p=1237");
            }
        });
        return v;
    }

    @Override
    protected void performViewClick(View v) {

    }
}
