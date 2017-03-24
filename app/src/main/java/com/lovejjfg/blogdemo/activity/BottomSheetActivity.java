package com.lovejjfg.blogdemo.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.lovejjfg.blogdemo.utils.BaseUtil;
import com.lovejjfg.blogdemo.utils.WebUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class BottomSheetActivity extends AppCompatActivity {
    @Bind(R.id.bottom_sheet)
    RecyclerView mSheet;
    @Bind(R.id.container)
    BottomSheet mContainer;
    @Bind(R.id.tv_title)
    TextView tittle;
    private static final String TAG = "RXJAVA";

    static final int TEXT = 0;
    static final int LOADING = 1;
    static boolean init = false;
    private int statusBarHeight;
//    private static ExecutorService service;
    private MyAdapter<BlogBean> adapter;
    private static final String HOST = "http://blog.csdn.net/lovejjfg/article/details/";
    private int statusHeight;
    private int currentPadding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
//        service = Executors.newCachedThreadPool();
        final ArrayList<BlogBean> beans = new ArrayList<>();
//        Observable.concat()
        /**
         *
         * */
        Observable.just("1", "2", "2", "3", "4")
                .distinct(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        Log.i(TAG, "callDistinct: "+s);
                        return Integer.parseInt(s);
                    }
                })
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        Log.i(TAG, "callMap: "+s);
                        return Integer.parseInt(s);
                    }
                })
//                .take(3)
//                .reduce(new Func2<Integer, Integer, Integer>() {
//                    @Override
//                    public Integer call(Integer integer, Integer integer2) {
//                        Log.i(TAG, "call: integer1:" + integer + "integer2:" + integer2);
//                        int i = integer + integer2;
//                        Log.i(TAG, "call: "+i);
//                        return i;
//                    }
//
//                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: " );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "onNext: " + integer);
                    }
                });
        Observable<Elements> observable = Observable.create(new Observable.OnSubscribe<Elements>() {
            @Override
            public void call(Subscriber<? super Elements> subscriber) {
                try {
                    Document document = Jsoup.connect("http://blog.csdn.net/lovejjfg").get();
                    Elements select = document.select("dl[class^=blog_list]");
                    SystemClock.sleep(1000);
                    subscriber.onNext(select);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        });
//        AndroidObservable
        observable.subscribeOn(Schedulers.io())
                .flatMap(new Func1<Elements, Observable<Element>>() {
                    @Override
                    public Observable<Element> call(Elements elements) {
                        return Observable.from(elements);
                    }
                })
                .map(new Func1<Element, BlogBean>() {

                    @Override
                    public BlogBean call(Element element) {
                        String tittle = element.select("a[href]").first().text();
                        String url = element.select("a").first().attr("href");
                        String id = url.substring(url.lastIndexOf("=") + 1);
                        url = HOST + id;
                        String time = element.select("em").first().text();
                        String times = element.select("a[href]").last().text();
//                        Log.i(TAG, "call: " + url);
                        return new BlogBean(tittle, url, time, times);

                    }
                })
//                .distinct()//可以来保证唯一性
//                .take(3)
//                .reduce(new Func2<BlogBean, BlogBean, BlogBean>() {
//                    @Override
//                    public BlogBean call(BlogBean blogBean, BlogBean blogBean2) {
//
//                        return null;
//                    }
//                })
                .distinct()
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BlogBean>>() {
                    @Override
                    public void onCompleted() {
//                        Log.i(TAG, "call: " + "onCompelete");
                        this.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        adapter.setDatas(null);
//                        Log.e(TAG, "call: ", e);
                        this.unsubscribe();
                    }

                    @Override
                    public void onNext(List<BlogBean> blogBeen) {
                        adapter.setDatas(blogBeen);

                    }
                });


//        service.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final ArrayList<BlogBean> beans = new ArrayList<>();
//                    Document document = Jsoup.connect("http://blog.csdn.net/lovejjfg").get();
//                    Elements select = document.select("dl[class^=blog_list]");
//                    for (Element element : select) {
//                        String tittle = element.select("a[href]").first().text();
//                        String url = element.select("a").first().attr("href");
//                        String id = url.substring(url.lastIndexOf("=") + 1);
//                        url = HOST + id;
//                        String time = element.select("em").first().text();
//                        String times = element.select("a[href]").last().text();
//                        BlogBean blogBean = new BlogBean(tittle, url, time, times);
//                        beans.add(blogBean);
////                        Log.i("Elements", "run: " + url);
//                    }
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapter.setDatas(beans);
//                        }
//                    });
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        statusBarHeight = BaseUtil.getStatusHeight(this);
        Log.i("TAG", "onCreate: " + statusBarHeight);

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
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
                int padding = (int) (statusBarHeight * percent);
                if (currentPadding == 0) {
                    currentPadding = padding;
                }
                int dy = padding - currentPadding;
                Log.i("TAG", "onSheetPositionScrolled: " + padding);
                tittle.setPadding(tittle.getPaddingLeft(), dy + tittle.getPaddingTop(), tittle.getPaddingRight(), tittle.getPaddingBottom());
                currentPadding = padding;
            }
        });

    }

    static class MyAdapter<T> extends RecyclerView.Adapter {
        List<T> datas;

        public void setDatas(List<T> datas) {
            this.datas =  datas;
            init = true;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TEXT:
                    return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog, parent, false));
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
                        ((MyHolder) holder).onBindHolder((BlogBean) t);
                        ((MyHolder) holder).mTvTittle.setText(((BlogBean) t).getTittle());
                        ((MyHolder) holder).mCardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rx.Observable<String> observable = rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
                                    @Override
                                    public void call(Subscriber<? super String> subscriber) {
                                        try {
                                            Document document = Jsoup.connect(((BlogBean) t).getUrl()).get();
                                            Element body = document.select("div[class^=blog_article_c]").first();
//                                            Element body = document.body();
                                            String data = WebUtils.BuildHtmlWithCss(body.toString(), null, false);
                                            Log.i(TAG, "call: " + data);
//                                            Element body = document.head();
                                            subscriber.onNext(data);
                                            subscriber.onCompleted();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            subscriber.onError(e);
                                        }
                                    }
                                });
                                observable.subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<String>() {
                                            @Override
                                            public void call(String s) {
                                                Intent i = new Intent(holder.itemView.getContext(), BrowserActivity2.class);
                                                i.putExtra("HOST", s);
                                                Log.i("TAG", "run: " + s);
                                                holder.itemView.getContext().startActivity(i);
                                            }
                                        }, new Action1<Throwable>() {
                                            @Override
                                            public void call(Throwable throwable) {
                                                Log.e("TAG", "call: ", throwable);
                                            }
                                        });


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
            if (datas == null) {
                return 1;
            }
            return init ? datas.size() : 1;
        }

    }

    static class MyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.blog_container)
        CardView mCardView;
        @Bind(R.id.tv_blog_tittle)
        TextView mTvTittle;
        @Bind(R.id.tv_blog_date)
        TextView mTvDate;
        @Bind(R.id.tv_blog_read_times)
        TextView mTvReadTimes;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void onBindHolder(BlogBean bean) {
            mTvTittle.setText(bean.getTittle());
            mTvDate.setText(bean.getTime());
            mTvReadTimes.setText(bean.getTimes());
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
