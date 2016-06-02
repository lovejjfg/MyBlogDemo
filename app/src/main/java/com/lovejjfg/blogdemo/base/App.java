package com.lovejjfg.blogdemo.base;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Joe on 2016-04-05
 * Email: lovejjfg@gmail.com
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
