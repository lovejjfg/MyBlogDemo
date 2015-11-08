package com.lovejjfg.blogdemo.utils;

import android.app.Activity;
import android.content.Intent;

import com.lovejjfg.blogdemo.R;

/**
 * Created by Administrator on 2015/11/8.
 */
public class BaseUtil {
    public static void startActivityOnspecifiedAnimation(Activity context, Class<?> cls) {
        context.startActivity(new Intent(context, cls));
        context.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }
}
