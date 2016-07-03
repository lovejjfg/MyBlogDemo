package com.lovejjfg.blogdemo.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.recyclerview.adapter.MyAdapter;

import butterknife.BindInt;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    private static int actionBarSize = -1;

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private double gridScrollY;
    private boolean flag;

    @BindInt(R.integer.num_columns)
    int columns;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        FrameLayout parent = (FrameLayout) findViewById(R.id.parent);
        parent.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        parent.post(new Runnable() {
//            @Override
//            public void run() {
//                Rect frame = new Rect();
//                getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//                final int statusBarHeight = frame.top;
//                final int toolbarHeight = toolbar.getHeight();
//                Log.e("statusBarHeight:", statusBarHeight + "");
//                Log.e("toolbarHeight:", toolbarHeight + "");
//                 /*init toolbar*/
//                ViewGroup.MarginLayoutParams lpToolbar = (ViewGroup.MarginLayoutParams) toolbar
//                        .getLayoutParams();
//
//                lpToolbar.topMargin = statusBarHeight;
//                Log.e("top1:", lpToolbar.topMargin + "");
//                toolbar.setLayoutParams(lpToolbar);
//                /*init statusBar*/
//                View statusBarBackground = findViewById(R.id.status);
//                FrameLayout.LayoutParams lpStatus = (FrameLayout.LayoutParams)
//                        statusBarBackground.getLayoutParams();
//                lpStatus.height = statusBarHeight;
//                statusBarBackground.setLayoutParams(lpStatus);
//                Log.e("status:", lpStatus.height + "");
//
//                /*init RecycleView*/
//                mRecyclerView.setPadding(mRecyclerView.getPaddingLeft(),
//                        statusBarHeight + toolbarHeight,
//                        mRecyclerView.getPaddingRight(), // landscape
//                        mRecyclerView.getPaddingBottom());
//                Log.e("recycleView-->Top", mRecyclerView.getPaddingTop() + "");
//
//            }
//        });

//        parent.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
//            @SuppressLint("NewApi")
//            @Override
//            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
//                if (!flag) {
//
//                    flag = true;
//
//                /*init toolbar*/
//                    ViewGroup.MarginLayoutParams lpToolbar = (ViewGroup.MarginLayoutParams) toolbar
//                            .getLayoutParams();
//
//                    lpToolbar.topMargin = insets.getSystemWindowInsetTop();
//                    Log.e("top1:", lpToolbar.topMargin + "");
//                    lpToolbar.rightMargin += insets.getSystemWindowInsetRight();
//                    toolbar.setLayoutParams(lpToolbar);
//                /*init statusBar*/
//                    View statusBarBackground = findViewById(R.id.status);
//                    FrameLayout.LayoutParams lpStatus = (FrameLayout.LayoutParams)
//                            statusBarBackground.getLayoutParams();
//                    lpStatus.height = insets.getSystemWindowInsetTop();
////                    statusBarBackground.setLayoutParams(lpStatus);
//                    Log.e("status:", lpStatus.height + "");
//
//                /*init RecycleView*/
//                    mRecyclerView.setPadding(mRecyclerView.getPaddingLeft(),
//                            insets.getSystemWindowInsetTop() + getActionBarSize
//                                    (Main2Activity.this),
//                            mRecyclerView.getPaddingRight() + insets.getSystemWindowInsetRight(), // landscape
//                            mRecyclerView.getPaddingBottom() + insets.getSystemWindowInsetBottom());
//                    Log.e("recycleView-->Top", mRecyclerView.getPaddingTop() + "");
//                }
//                return insets.consumeSystemWindowInsets();
//            }
//        });

//        toolbar.post(new Runnable() {
//            @Override
//            public void run() {
//                initHeight();
//            }
//        });
        GridLayoutManager manager = new GridLayoutManager(this, columns);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(new MyAdapter());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                gridScrollY += dy;
//                Log.e("gridScrollY:", gridScrollY + "");
//                if (gridScrollY > 0 && toolbar.getTranslationZ() != -1f) {
//                    toolbar.setTranslationZ(-1f);
//                    Log.i("TranslationZ", -1 + "啦");
//                } else if (gridScrollY <= 0 && toolbar.getTranslationZ() != 0) {
//                    toolbar.setTranslationZ(0f);
//                    Log.i("TranslationZ", "重置零啦");
//                }
            }
        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        initHeight();
    }

    @NonNull
    private void initHeight() {
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        final int statusBarHeight = frame.top;
        final int toolbarHeight = toolbar.getHeight();
        Log.e("statusBarHeight:", statusBarHeight + "");
        Log.e("toolbarHeight:", toolbarHeight + "");
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {

                mRecyclerView.setPadding(mRecyclerView.getPaddingLeft(), mRecyclerView.getPaddingTop() + toolbarHeight + 500, mRecyclerView.getPaddingRight(), mRecyclerView.getPaddingBottom());
                mRecyclerView.scrollBy(0, -(toolbarHeight));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static int getActionBarSize(Context context) {
        if (actionBarSize < 0) {
            TypedValue value = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.actionBarSize, value, true);
            actionBarSize = TypedValue.complexToDimensionPixelSize(value.data, context
                    .getResources().getDisplayMetrics());
        }
        Log.e("actionBarSize:", actionBarSize + "");
        return actionBarSize;
    }
}
