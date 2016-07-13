package com.lovejjfg.blogdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lovejjfg.blogdemo.ui.HeaderView;
import com.lovejjfg.blogdemo.ui.ScrollAbleViewPager;
import com.lovejjfg.blogdemo.ui.indicator.RectPageIndicator;
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
    private ArrayList<Fragment6> fragments;
    private String name;
    private DNADialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        savedInstanceState = null;
        super.onCreate(null);
        setContentView(R.layout.activity_lock_list);
        ButterKnife.bind(this);
        fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fragments.add(Fragment6.newInstance(String.format("第%d个", i)));
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
                fragments.get(vp.getCurrentItem()).setText(name);
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

    class DNADialog extends AlertDialog {

        public DNADialog(Context context) {
            this(context,R.style.Dialog_Fullscreen);
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
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_dna_guide, (ViewGroup) getWindow().getDecorView(), false);
            setContentView(R.layout.layout_dna_guide);
//            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            layoutParams.setMargins(200, 0, 200, 0);
//            setContentView(view, layoutParams);
//            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 1400);

        }
    }

}
