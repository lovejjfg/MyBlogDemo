package com.lovejjfg.blogdemo.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lovejjfg.blogdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toast toast;
    @Bind(R.id.scrollView)
    NestedScrollView scrollView;
    @Bind(R.id.iv)
    ImageView mIv;
    @Bind(R.id.tv1)
    TextView mTv1;
    boolean showStatus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling1);
        ButterKnife.bind(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        mIv.setOnClickListener(this);
//        mView.setOnClickListener(this);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
//        if (appBarLayout != null) {
//            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//                @Override
//                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                    Log.e("onOffsetChanged:", verticalOffset + "");
//                }
//            });
//        }

//        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.e("scrollY:", scrollY + "");
//                Log.e("mTv1", "translateY: " + mTv1.getTranslationY());
//                Log.e("mTv1", "scroolY: " + mTv1.getScrollY());
//                Log.e("mTv1", "Top: " + mTv1.getTop());
//                Log.e("mTv1", "Bottom: " + mTv1.getBottom());
//                int minimumHeight = mIv.getMinimumHeight();
//                int measuredHeight = mIv.getMeasuredHeight();
//                int dy = measuredHeight - minimumHeight;
//                int scrool = Math.min(dy, scrollY);
//                mIv.setTranslationY((float) (-scrool*0.8));
//                if (scrool == dy) {
//                    mIv.setPressed(true);
//                    mIv.bringToFront();
//                    mIv.requestLayout();
//                    mIv.getParent().requestLayout();
////                    mIv.refreshDrawableState();
//                } else {
//                    mIv.setPressed(false);
////                    mIv.refreshDrawableState();
//                }
//            }
//        });
        mTv1.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.e("mTv1", "oldTop: " + oldTop);
                Log.e("mTv1", "top: " + top);
                Log.e("mTv1", "translateY: " + v.getTranslationY());

            }
        });


        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

    }

    @Override
    public void onClick(View v) {
        toast.setText(v.getId() + "");
        toast.show();
        showStatus = !showStatus;
        full(showStatus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        toast.setText(item.getItemId() + "");
        toast.show();
        return true;
    }

    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

}
