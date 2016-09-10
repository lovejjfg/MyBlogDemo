package com.lovejjfg.blogdemo.activity;

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
import android.widget.FrameLayout;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.recyclerview.adapter.MyAdapter;
import com.lovejjfg.blogdemo.ui.HeaderRefreshLayout;
import com.lovejjfg.blogdemo.ui.TouchCircleView;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity implements TouchCircleView.OnLoadingListener {

    private static int actionBarSize = -1;

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private double gridScrollY;
    private boolean flag;

    @BindInt(R.integer.num_columns)
    int columns;
    @Bind(R.id.header)
    HeaderRefreshLayout mHeader;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        FrameLayout parent = (FrameLayout) findViewById(R.id.parent);
//        parent.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
//        SpacesItemDecoration decoration = new SpacesItemDecoration(20);
        GridSpacingItemDecoration decoration = new GridSpacingItemDecoration(columns, 20, false, 0);
        mRecyclerView.addItemDecoration(decoration);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHeader.setRefresh(true);
        mHeader.addLoadingListener(new TouchCircleView.OnLoadingListener() {
            @Override
            public void onProgressStateChange(int state, boolean hide) {

            }

            @Override
            public void onProgressLoading() {

            }
        });

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

    @Override
    public void onProgressStateChange(int state, boolean hide) {

    }

    @Override
    public void onProgressLoading() {

    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;
        private int headerNum;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge, int headerNum) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
            this.headerNum = headerNum;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view) - headerNum; // item position

            if (position >= 0) {
                int column = position % spanCount; // item column

                if (includeEdge) {
                    if (column == 0) {
                        outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    }
                    if (column == spanCount - 1) {
                        outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
                    }

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                    outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                    if (position >= spanCount) {
                        outRect.top = spacing; // item top
                    }
                }
            } else {
                outRect.left = 0;
                outRect.right = 0;
                outRect.top = 0;
                outRect.bottom = 0;
            }
        }
    }
}

