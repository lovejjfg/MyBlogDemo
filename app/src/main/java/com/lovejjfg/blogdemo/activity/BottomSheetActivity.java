package com.lovejjfg.blogdemo.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.model.bean.BlogBean;
import com.lovejjfg.blogdemo.ui.BottomSheet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
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
    private ExecutorService service;
    private MyAdapter<BlogBean> adapter;
    private static final String HOST = "http://blog.csdn.net/lovejjfg/article/details/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        service = Executors.newCachedThreadPool();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final ArrayList<BlogBean> beans = new ArrayList<>();
                    Document document = Jsoup.connect("http://blog.csdn.net/lovejjfg").get();
                    Elements select = document.select("dl[class^=blog_list]");
                    for (Element element : select) {
                        String tittle = element.select("a[href]").first().text();
                        String url = element.select("a").first().attr("href");
                        String id = url.substring(url.lastIndexOf("=") + 1);
                        url = HOST + id;
                        String time = element.select("em").first().text();
                        String times = element.select("a[href]").last().text();
                        BlogBean blogBean = new BlogBean(tittle, url, time, times);
                        beans.add(blogBean);
                        Log.i("Elements", "run: " + url);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setDatas(beans);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
        adapter = new MyAdapter<>();
        mSheet.setAdapter(adapter);

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

            }
        });

    }

    static class MyAdapter<T> extends RecyclerView.Adapter {
        ArrayList<T> datas;

        public void setDatas(ArrayList<T> datas) {
            this.datas = datas;
            init = true;
            notifyDataSetChanged();
        }

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
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);
            switch (type) {
                case TEXT:
                    final T t = datas.get(position);
                    if (t instanceof BlogBean) {
                        ((MyHolder) holder).mTv.setText(((BlogBean) t).getTittle());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(holder.itemView.getContext(), BrowserActivity2.class);
                                i.putExtra("HOST", ((BlogBean) t).getUrl());
                                holder.itemView.getContext().startActivity(i);

                            }
                        });
                    }
                    break;
                case LOADING:
                    ((MyLoadingHolder) holder).progressBar.setVisibility(View.VISIBLE);
                    break;

            }
        }

        @Override
        public int getItemViewType(int position) {
            return init ? TEXT : LOADING;
        }

        @Override
        public int getItemCount() {
            return init ? datas.size() : 1;
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
