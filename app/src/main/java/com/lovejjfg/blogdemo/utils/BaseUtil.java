package com.lovejjfg.blogdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

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

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}
