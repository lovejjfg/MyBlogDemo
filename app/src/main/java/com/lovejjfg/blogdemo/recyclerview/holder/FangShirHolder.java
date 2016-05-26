package com.lovejjfg.blogdemo.recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Joe on 2016-03-24
 * Email: lovejjfg@163.com
 */
public class FangShirHolder extends RecyclerView.ViewHolder {
    public TextView mTittle;

    public FangShirHolder(View itemView) {
        super(itemView);
        mTittle = (TextView) itemView;
    }


    public void bindFangshi(String tittle) {
        mTittle.setText(tittle);
    }

}
