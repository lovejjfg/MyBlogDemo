package com.lovejjfg.blogdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.activity.BottomSheetActivity;
import com.lovejjfg.blogdemo.activity.MainActivity;

/**
 * Created by Joe on 2016-05-24
 * Email: lovejjfg@163.com
 */
public class JumpUtil {

    public static void jumpToMain(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void jumpToBottome(Activity context) {
        Intent intent = new Intent(context, BottomSheetActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.bottom_activity_in, R.anim.bottom_activity_out);

    }
}
