package com.lovejjfg.blogdemo.activity;

import android.graphics.Rect;
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
import com.lovejjfg.blogdemo.ui.BottomSheet;

import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BottomSheetActivity extends AppCompatActivity {
    @Bind(R.id.bottom_sheet)
    RecyclerView mSheet;
    @Bind(R.id.container)
    BottomSheet mContainer;

    static final int TEXT = 0;
    static final int LOADING = 1;
    static boolean init = false;
    private int statusBarHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        ButterKnife.bind(this);
        init = false;
        initSheet();


    }

    private void initSheet() {
        mSheet.setAdapter(new MyAdapter());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mSheet.setLayoutManager(manager);

        mContainer.registerCallback(new BottomSheet.Callbacks() {
            @Override
            public void onSheetDismissed() {
                super.onSheetDismissed();
                BottomSheetActivity.this.finishAfterTransition();
            }

            @Override
            public void onSheetPositionChanged(int sheetTop, boolean userInteracted) {
                super.onSheetPositionChanged(sheetTop, userInteracted);
            }

            @Override
            public void onSheetPositionScrolled(float percent) {
                super.onSheetPositionScrolled(percent);
//                mSheet.set
                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

            }
        });

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
