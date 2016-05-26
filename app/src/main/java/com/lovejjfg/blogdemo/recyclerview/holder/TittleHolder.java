package com.lovejjfg.blogdemo.recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by Joe on 2016-03-24
 * Email: lovejjfg@163.com
 */
public class TittleHolder extends RecyclerView.ViewHolder {
    private TextView tvTittle;

    public TittleHolder(View itemView) {
        super(itemView);
        tvTittle = (TextView) itemView;
    }

    public void bindTittleView(String tittle) {
        tvTittle.setText(tittle);
    }
}
