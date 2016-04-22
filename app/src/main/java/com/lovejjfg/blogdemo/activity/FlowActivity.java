package com.lovejjfg.blogdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.TextView;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.ui.FlowLayout1;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FlowActivity extends AppCompatActivity {

    @Bind(R.id.flow)
    FlowLayout1 flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        ButterKnife.bind(this);
        for (int i = 0; i < 10; i++) {
            TextView textView = new TextView(this);
            textView.setText("xiixixixi" + i);
            textView.setTextColor(Color.RED);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            flow.addView(textView);
        }


    }
}
