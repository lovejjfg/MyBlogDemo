package com.lovejjfg.blogdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovejjfg.blogdemo.R;
import com.lovejjfg.blogdemo.ui.HorizontalPicker;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.lovejjfg.blogdemo.ui.HorizontalPicker.PickerItemAdapter;

public class PickerActivity extends AppCompatActivity {
    @Bind(R.id.hor_picker)
    HorizontalPicker mHorPicker;
    private boolean isExband;
    private static final String TAG = PickerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        ButterKnife.bind(this);
        mHorPicker.setPickerAdapter(new PickerItemAdapter() {

            @Override
            public ItemHolder onCreatePickerHolder(ViewGroup parent, int pos) {

                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                RecyclerView recycler = (RecyclerView) inflater.inflate(R.layout.text_layout, parent, false);
                View inflate = inflater.inflate(R.layout.layout_item, parent, false);
                return new ItemHolder(inflate, recycler, pos + 18);
            }

//            @Override
//            public HorizontalPicker.PickerHolder onCreatePickerDetailView(ViewGroup parent, int pos) {
//                TextView textView = new TextView(parent.getContext());
//                textView.setText(String.format("xxxxxxxx%d", pos));
//                return new ItemDetailHolder(textView);
//            }

            @Override
            public int getItemCount() {
                return 3;
            }

            @Override
            public void onBindPickerHolder(ViewGroup parent, HorizontalPicker.PickerHolder holder, int pos) {
                ((ItemHolder) holder).onBindHolder("这是" + pos);
//                holder.onBindHolder();
            }
        });

    }

    public static class ItemHolder extends HorizontalPicker.PickerHolder<View, RecyclerView> {
        @Bind(R.id.tv_title)
        TextView mTextTitle;
        @Bind(R.id.iv_arrow)
        ImageView mIvArrow;


        public ItemHolder(View view, RecyclerView detailView, final int i) {
            super(view, detailView);
            detailView.setLayoutManager(new GridLayoutManager(detailView.getContext(), 4));
            detailView.setAdapter(new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return new MyHolder(new TextView(parent.getContext()));
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                    ((TextView) holder.itemView).setText(position == 0 ? "默认" : "点击" + position);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBindHolder(position == 0 ? "默认" : "点击" + position);

                        }
                    });
                }

                @Override
                public int getItemCount() {
                    return i;
                }
            });
            ButterKnife.bind(this, view);
        }


        @Override
        public void notifyCheckChange(ViewGroup horizontalPicker, boolean checked, boolean toggle) {
            mIvArrow.setRotation(checked ? 180 : 0);
//            if (toggle) {
//                com.transitionseverywhere.TransitionManager.beginDelayedTransition(horizontalPicker, new Explode());
//            }
//            mItemDetailView.setVisibility(checked ? View.VISIBLE : View.GONE);
        }


        public void onBindHolder(String title) {
            mTextTitle.setText(title);
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(View itemView) {
            super(itemView);
        }
    }

//    public static class ItemDetailHolder extends HorizontalPicker.PickerHolder {
//        public ItemDetailHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//
//        @Override
//        public void notifyCheckChange(boolean checked) {
//
//        }
//    }

}
