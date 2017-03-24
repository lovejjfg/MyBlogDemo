/*
 *  Copyright (c) 2017.  Joe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.lovejjfg.blogdemo.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lovejjfg.blogdemo.activity.PickerActivity;
import com.transitionseverywhere.Explode;
import com.transitionseverywhere.TransitionManager;

import java.util.ArrayList;

/**
 * Created by Joe on 2017/3/21.
 * Email lovejjfg@gmail.com
 */
//相当于两级菜单选择，parentId itemId
public class HorizontalPicker extends LinearLayout implements View.OnClickListener {

    private PickerItemAdapter mAdapter;
    private ArrayList<PickerHolder> mHolders;
    private LinearLayout header;
    private View currentDetail;
    private boolean isExband;
    private FrameLayout mDetailContainer;

    public HorizontalPicker(Context context) {
        this(context, null);
    }

    public HorizontalPicker(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HorizontalPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        header = new LinearLayout(context);
        header.setOrientation(LinearLayout.HORIZONTAL);
        float density = getResources().getDisplayMetrics().density;
        addView(header, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (density * 56)));
        mDetailContainer = new FrameLayout(context);
        addView(mDetailContainer, new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    public void setPickerAdapter(PickerItemAdapter adapter) {
        if (adapter != null) {
            mAdapter = adapter;
        }
        int count = mAdapter.getItemCount();
        mHolders = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final PickerHolder pickerHolder = mAdapter.onCreatePickerHolder(this, i);
            mHolders.add(pickerHolder);
            View view = pickerHolder.mItemView;
            mAdapter.onBindPickerHolder(this, pickerHolder, i);
            header.addView(view);
        }

        for (PickerHolder holder : mHolders) {
            holder.mItemView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        for (PickerHolder holder : mHolders) {
            isExband = false;
            if (holder.mItemView == v) {
                holder.toggle();
                if (holder.isChecked()) {
                    showDetail();
                    if (currentDetail == holder.mItemDetailView) {
                        holder.notifyCheckChange(this, holder.isChecked(), !isExband);
                        break;
                    }
                    if (currentDetail != null) {
                        mDetailContainer.removeView(currentDetail);
                    }
                    currentDetail = holder.mItemDetailView;
                    mDetailContainer.addView(currentDetail);
                    holder.notifyCheckChange(this, holder.isChecked(), !isExband);
                } else {
                    dismissDetail();
                    holder.notifyCheckChange(this, holder.isChecked(), isExband);
                }
            } else {
                holder.setChecked(false);
                holder.notifyCheckChange(this, holder.isChecked(), false);
            }
        }

    }

    private void showDetail() {
        if (mDetailContainer.getVisibility() != VISIBLE) {
            TransitionManager.beginDelayedTransition(this, new Explode());
            mDetailContainer.setVisibility(VISIBLE);
        }
    }

    private void dismissDetail() {
        if (mDetailContainer.getVisibility() == VISIBLE) {
            TransitionManager.beginDelayedTransition(this, new Explode());
            mDetailContainer.setVisibility(GONE);
        }
    }

//    public static class MyPickerItem implements PickerItem, OnClickListener {
//
//        private final Context mContext;
//        private TextView mText;
//        private PickerItemClick mPickerItemClick;
//
//        public MyPickerItem(Context context) {
//            mContext = context;
//        }
//
//        @Override
//        public View onCreatePickerHolder() {
//            mText = (TextView) LayoutInflater.from(mContext).inflate(R.layout.text_layout, null, false);
//            mText.setOnClickListener(this);
//            return mText;
//        }
//
//        @Override
//        public void onChildItemClicked(int pos) {
//
//        }
//
//        @Override
//        public void onChildItemShown() {
//
//        }
//
//        @Override
//        public void onChildItemClosed() {
//
//        }
//
//        @Override
//        public void setPickerItemText(CharSequence text) {
//            mText.setText(text);
//        }
//
//        @Override
//        public void setPickerItemClickListener(PickerItemClick pickerItemClick) {
//            mPickerItemClick = pickerItemClick;
//        }
//
//        @Override
//        public void onClick(View v) {
//
//        }
//    }

    public interface PickerItem {
        PickerActivity.ItemHolder onCreatePickerHolder(ViewGroup parent, int pos);

//        PickerHolder onCreatePickerDetailView(ViewGroup parent, int pos);

        int getItemCount();

//        void onChildItemClicked(ViewGroup parent, View item, int pos);
//
//        void onChildItemShown(ViewGroup parent, View view);
//
//        void onChildItemClosed(ViewGroup parent, View view);

        void onBindPickerHolder(ViewGroup parent, PickerHolder pickerItem, int pos);

    }

    public static abstract class PickerItemAdapter implements PickerItem {

    }

    public interface ItemDetailClickListener {
        void onDetailItemClick(int pos);
    }

    public static abstract class PickerHolder<P extends View, I extends View> implements Checkable {

        public P mItemView;
        public I mItemDetailView;
        private boolean isChecked;

        public PickerHolder(P view, I detailView) {
            mItemView = view;
            mItemDetailView = detailView;
        }

        @Override
        public boolean isChecked() {
            return isChecked;
        }

        @Override
        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        @Override
        public void toggle() {
            isChecked = !isChecked;
        }

        public abstract void notifyCheckChange(ViewGroup horizontalPicker, boolean holderChecked, boolean toggle);

    }


}
