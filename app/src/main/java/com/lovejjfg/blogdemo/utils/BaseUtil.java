package com.lovejjfg.blogdemo.utils;

import android.app.Activity;
import android.content.Intent;

import com.lovejjfg.blogdemo.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/11/8.
 */
public class BaseUtil {
    public static void startActivityOnspecifiedAnimation(Activity context, Class<?> cls) {
        context.startActivity(new Intent(context, cls));
        context.overridePendingTransition(R.anim.start_activity_in, R.anim.start_activity_out);
    }


    public static int getStatusHeight(Activity context) {
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
