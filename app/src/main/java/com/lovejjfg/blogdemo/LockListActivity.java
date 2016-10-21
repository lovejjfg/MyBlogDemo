package com.lovejjfg.blogdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextClock;
import android.widget.TextView;

import com.lovejjfg.blogdemo.ui.HeaderView;
import com.lovejjfg.blogdemo.ui.ScrollAbleViewPager;
import com.lovejjfg.blogdemo.ui.indicator.RectPageIndicator;
import com.lovejjfg.blogdemo.utils.BaseUtil;
import com.lovejjfg.fragment.Fragment6;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LockListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, HeaderView.OnHeaderRefreshListener {
    @Bind(R.id.header)
    public HeaderView headerView;
    @Bind(R.id.indicator)
    RectPageIndicator indicator;
    @Bind(R.id.vp)
    ScrollAbleViewPager vp;
    @Bind(R.id.tv_addr)
    TextView mAddr;
    private ArrayList<Fragment> fragments;
    private String name;
    private DNADialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        savedInstanceState = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_list);
        ButterKnife.bind(this);
        fragments = new ArrayList<>();
        if (savedInstanceState != null) {
            fragments.addAll(getSupportFragmentManager().getFragments());
        } else {
            for (int i = 0; i < 4; i++) {
                fragments.add(Fragment6.newInstance(String.format("第%d个", i)));
            }
        }
        vp.setOffscreenPageLimit(fragments.size());
        dialog = new DNADialog(this);
        headerView.setOnHeaderRefreshListener(this);
        headerView.enablePullDownRefresh();
        headerView.disablePullUpRefresh();
        headerView.hideFooterView();
//        mAddr.setText(R.string.large_text);
        init();
//        headerView.setRefresh();
    }

    private void init() {
        vp.setHorizontalFadingEdgeEnabled(true);
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return super.getPageTitle(position);
            }
        });
        vp.setCurrentItem(0);

        vp.addOnPageChangeListener(this);
        indicator.setViewPager(vp);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        name = String.format("这是第%d的地址哟！！！！", position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onHeaderRefresh(HeaderView view) {
        vp.setScrollable(false);
        headerView.postDelayed(new Runnable() {
            @Override
            public void run() {
//                headerView.onHeaderRefreshComplete();
                headerView.onHeaderRefreshError(getString(R.string.pull_to_refresh_load_error));
                if (fragments.get(vp.getCurrentItem()) instanceof Fragment6) {
                    ((Fragment6) fragments.get(vp.getCurrentItem())).setText(name);
                }
            }
        }, 3000);
    }

    @Override
    public void onHeaderRefreshFinished() {
        vp.setScrollable(true);
    }

    public HeaderView getHeaderView() {
        return headerView;
    }

    public void showTheDialog() {
        dialog.show();
    }

    public static class DNADialog extends AlertDialog {

        public DNADialog(Context context) {
            this(context, R.style.Dialog_Fullscreen);
            initDialog();
        }


        public DNADialog(Context context, int themeResId) {
            super(context, themeResId);
        }

        protected DNADialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        private void initDialog() {


        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_dna_guide);
            setCanceledOnTouchOutside(true);
//            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            layoutParams.setMargins(200, 0, 200, 0);
//            setContentView(view, layoutParams);
            int windowWidth = getWindowWidth(getContext());
            getWindow().setLayout((int) (windowWidth *0.8f), dip2px(getContext(), 400));

        }

        /**
         * 获取屏幕高度
         *
         * @param context
         * @return
         */
        public static int getWindowHeight(Context context) {
            return context.getResources().getDisplayMetrics().heightPixels;
        }

        /**
         * 获取屏幕宽度
         *
         * @param context
         * @return
         */
        public static int getWindowWidth(Context context) {
            return context.getResources().getDisplayMetrics().widthPixels;
        }

        public static int dip2px(Context context, float dipValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        }
    }

}
