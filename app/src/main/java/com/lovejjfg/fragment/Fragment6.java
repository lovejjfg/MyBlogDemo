package com.lovejjfg.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lovejjfg.blogdemo.LockListActivity;
import com.lovejjfg.blogdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joe on 2016-06-09
 * Email: zhangjun166@pingan.com.cn
 */
public class Fragment6 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
//    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String NAME = "name";
    private boolean isVisible;

    public Fragment6() {
    }

    @Bind(R.id.tv_name)
    TextView mName;
    @Bind(R.id.scrollView)
    NestedScrollView mScrollView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment6 newInstance(String name) {
        Fragment6 fragment = new Fragment6();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        savedInstanceState = null;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab5, container, false);
        ButterKnife.bind(this, rootView);
        mName.setText(getArguments().getString(NAME, "初始化！"));
//        mScrollView.setEnabled(false);

        ((LockListActivity) getActivity()).getHeaderView().setScrollView(mScrollView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", "onResume: " + getArguments().getString(NAME, "初始化！"));
        if (!((LockListActivity) getActivity()).headerView.isHeaderRefreshing()) {
            ((LockListActivity) getActivity()).headerView.setRefresh();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;
        Log.e("TAG", "setUserVisibleHint: " + isVisibleToUser + getArguments().getString(NAME, "初始化！"));
        super.setUserVisibleHint(isVisibleToUser);
        if (getActivity() != null && isVisibleToUser) {
            ((LockListActivity) getActivity()).headerView.setRefresh();
        }
    }

    public void setText(String name) {
        mName.setText(name);
    }
}
