package com.lovejjfg.blogdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.recyclerview.TouchHelperCallback;
import com.lovejjfg.blogdemo.recyclerview.holder.FangShirHolder;
import com.lovejjfg.blogdemo.recyclerview.holder.MoreFangshiHolder;
import com.lovejjfg.blogdemo.recyclerview.holder.MoreHolder;
import com.lovejjfg.blogdemo.recyclerview.holder.OnShowMoreClickListener;
import com.lovejjfg.blogdemo.recyclerview.holder.TittleHolder;

import java.util.ArrayList;
import java.util.Collections;

public class FangshiActivity extends AppCompatActivity implements OnShowMoreClickListener {

    private RecyclerView recyclerView;
    private ArrayList<Item> items;
    private ArrayList<Item> moreItems = new ArrayList<>();


    boolean hasMore = false;
    private FangshiAdapter adapter;
    private boolean isShow = false;
    private ArrayList<Item> moreFangshi;
    private Item mCurrent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fangshi);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        GridLayoutManager manager1 = new GridLayoutManager(this, 3);
        GridView gridView = new GridView(this);
        gridView.setNumColumns(3);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new FangshiAdapter();
        recyclerView.setAdapter(adapter);
        //初始化一个TouchHelperCallback
        TouchHelperCallback callback = new TouchHelperCallback();
        //添加一个回调
        callback.setItemTouchCallBack(adapter);
        //初始化一个ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        //关联相关的RecycleView
        itemTouchHelper.attachToRecyclerView(recyclerView);

        initData();

    }

    private void initData() {
        items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Item item = new Item(Type.TITTLE);
            items.add(item);
            item.tittle = "标题" + i;
            int j = (int) (Math.random() * 10);
            if (j > 2) {
                hasMore = true;
                moreFangshi = new ArrayList<>();
            }
            for (int k = 0; k <= j; k++) {
                if (k <= 2) {
                    Item normal = new Item(Type.FANGSHI);
                    normal.tittle = "房事" + k;
                    items.add(normal);
                } else {
                    Item i1 = new Item(Type.MORE_FANGSHI);
                    i1.tittle = "更多房事" + k;
                    moreFangshi.add(i1);
                }
            }
            if (hasMore) {
                Item more = new Item(Type.MORE);
                more.list = moreFangshi;
                more.tittle = "点我还有";
                items.add(more);
                moreItems.add(more);
                hasMore = false;
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(Item item) {
        Log.e("点击--是否显示：：：：", item.isShow + "");
        Log.i("item:", item.toString());
        mCurrent = item;
        //// TODO: 2016-03-24 more的点击
        int position = items.indexOf(item);
        if (position <= 0 || position >= items.size()) {
            return;
        }
        //这个position就是more的类型
        if (item.type == Type.MORE && item.list.size() > 0) {
            for (Item item1 : item.list) {
                if (item.isShow) {
                    items.add(position, item1);
                    adapter.notifyItemInserted(position);
                } else {
                    int pos = items.indexOf(item1);
                    if (pos > 0 && pos < items.size()) {
                        items.remove(pos);
                        adapter.notifyItemRemoved(pos);
                    }
                }
            }
        }

    }

    class FangshiAdapter extends RecyclerView.Adapter implements TouchHelperCallback.ItemTouchCallBack {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case Type.TITTLE:
                    TextView textView = new TextView(parent.getContext());
                    textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(50);
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));

                    return new TittleHolder(textView);
                case Type.FANGSHI:
                    TextView textView1 = new TextView(parent.getContext());
                    textView1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView1.setGravity(Gravity.CENTER);
                    textView1.setTextSize(40);
                    textView1.setTextColor(getResources().getColor(R.color.default_blue_color));
                    return new FangShirHolder(textView1);
                case Type.MORE_FANGSHI:
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_morefangshi, parent, false);
                    return new MoreFangshiHolder(view);
                case Type.MORE:
                    TextView textView3 = new TextView(parent.getContext());
                    textView3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView3.setGravity(Gravity.CENTER);
                    textView3.setTextSize(80);
                    textView3.setTextColor(getResources().getColor(R.color.colorPrimary));
                    return new MoreHolder(textView3, FangshiActivity.this);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            //倒着通知更改
            switch (getItemViewType(position)) {
                case Type.TITTLE:
                    ((TittleHolder) holder).bindTittleView(items.get(position).tittle);
                    break;
                case Type.FANGSHI:
                    ((FangShirHolder) holder).bindFangshi(items.get(position).tittle);
                    break;
                case Type.MORE_FANGSHI:
                    ((MoreFangshiHolder) holder).bindMoreFangshi(items.get(position).tittle, items.get(position).isShow);
                    break;
                case Type.MORE:
                    ((MoreHolder) holder).bindMore(items.get(position), position);
                    Log.i("更多的位置：", position + "");
                    Log.i("item:", items.get(position).toString());
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int position) {
            return items.get(position).type;
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(items, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(items, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemDismiss(int position) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    interface Type {
        int TITTLE = 0;
        int FANGSHI = 1;
        int MORE_FANGSHI = 2;
        int MORE = 3;
    }

    public static class Item {
        public Item(int type) {
            this.type = type;
        }


        public int type;
        public String tittle;
        public ArrayList<Item> list;
        public boolean isShow = false;
    }

    //        for (int i = position - 1; i > 0; i--) {
//            if (items.get(i).type != Type.MORE_FANGSHI) {
//                return;
//            }
//            if (items.get(i).type == Type.MORE_FANGSHI) {
//                adapter.notifyItemChanged(i);
//            }
//        }
}







