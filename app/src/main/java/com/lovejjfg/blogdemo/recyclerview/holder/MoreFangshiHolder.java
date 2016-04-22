package com.lovejjfg.blogdemo.recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lovejjfg.blogdemo.R;


/**
 * Created by Joe on 2016-03-24
 * Email: zhangjun166@pingan.com.cn
 */
public class MoreFangshiHolder extends RecyclerView.ViewHolder {
    private TextView tvName;
    public   TextView mDelete;

    public MoreFangshiHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_more);
//        mDelete = (TextView) itemView.findViewById(R.id.tv_delete);
        //更多的默认隐藏的
    }


    public void bindMoreFangshi(String tittle, boolean isShow) {
        Log.e("是否显示：", isShow + "");
        tvName.setText(tittle);
    }
    public View getDeleteView() {
        return mDelete;
    }
}
