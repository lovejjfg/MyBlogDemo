package com.lovejjfg.blogdemo.base;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BasePresenterImpl{

    protected ExecutorService pool = Executors.newFixedThreadPool(4);
    protected Handler mMainHandler = new Handler(Looper.getMainLooper());

    protected void doBackground(Runnable runnable)
    {
        pool.execute(runnable);
    }


    protected void UpdateInMainThread(Runnable runnable)
    {
        if(mMainHandler!=null)
        {
            mMainHandler.post(runnable);
        }
    }

    protected void UpdateInMainThreadDelay(Runnable runnable, long millis)
    {
        if(mMainHandler!=null)
        {
            mMainHandler.postDelayed(runnable, millis);
        }
    }

    protected void checkBackground()
    {
       if(Looper.getMainLooper() == Looper.myLooper())
       {
           throw new RuntimeException("it must be running in work thread!");
       }
    }
}
