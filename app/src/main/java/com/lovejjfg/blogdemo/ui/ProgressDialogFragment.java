package com.lovejjfg.blogdemo.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lovejjfg.blogdemo.R;

/**
 * Created by Joe on 2017/2/21.
 * Email lovejjfg@gmail.com
 */

public class ProgressDialogFragment extends DialogFragment {

    private ProgressBar mProgress;
    private int progress = 1;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            mProgress.setProgress(progress++);
            if (mProgress.getProgress() == 100) {
                mHandler.removeCallbacksAndMessages(null);
            } else {
                mHandler.sendEmptyMessageDelayed(1, 100);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                mProgress.setProgress(progress++);
                if (mProgress.getProgress() == 100) {
                    mHandler.removeCallbacksAndMessages(null);
                } else {
                    mHandler.sendEmptyMessageDelayed(1, 100);
                }
                super.handleMessage(msg);
            }
        };
        if (savedInstanceState != null) {
            progress = savedInstanceState.getInt("PROGRESS");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_progress, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgress = (ProgressBar) view.findViewById(R.id.progressbar);
        mProgress.setMax(100);
        mHandler.sendEmptyMessage(1);
//        View cancelButton = view.findViewById(android.R.id.button2);
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//        View confirmButton = view.findViewById(android.R.id.button1);
//        confirmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("PROGRESS", progress);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDetach();
    }
}
