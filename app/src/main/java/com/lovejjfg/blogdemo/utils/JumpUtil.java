package com.lovejjfg.blogdemo.utils;

import android.content.Context;
import android.content.Intent;

import com.lovejjfg.blogdemo.activity.MainActivity;

/**
 * Created by Joe on 2016-05-24
 * Email: zhangjun166@pingan.com.cn
 */
public class JumpUtil {

    public static void jumpToMain(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
