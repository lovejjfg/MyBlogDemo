package com.lovejjfg.blogdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lovejjfg.blogdemo.R;

import java.text.MessageFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BottomSheetActivity extends AppCompatActivity {
    @Bind(R.id.bottom_sheet)
    RecyclerView mSheet;

    static final int TEXT = 0;
    static final int LOADING = 1;
    static boolean init = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        ButterKnife.bind(this);
        init = false;
        initSheet();

    }

    private void initSheet() {
        mSheet.setAdapter(new MyAdapter());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mSheet.setLayoutManager(manager);

    }

    static class MyAdapter extends RecyclerView.Adapter {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TEXT:
                    return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_text, parent, false));
                case LOADING:
                    return new MyLoadingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_footer, parent, false));
                default:
                    return null;
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);
            switch (type) {
                case TEXT:
                    ((MyHolder) holder).mTv.setText(MessageFormat.format("Test{0}", position));
                    break;
                case LOADING:
                    ((MyLoadingHolder) holder).progressBar.setVisibility(View.VISIBLE);
                    holder.itemView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            init = true;
                            notifyDataSetChanged();
                        }
                    }, 3000);
                    break;

            }
        }

        @Override
        public int getItemViewType(int position) {
            return init ? TEXT : LOADING;
        }

        @Override
        public int getItemCount() {
            return init ? 50 : 1;
        }
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text)
        TextView mTv;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class MyLoadingHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.progressbar)
        ProgressBar progressBar;

        public MyLoadingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
