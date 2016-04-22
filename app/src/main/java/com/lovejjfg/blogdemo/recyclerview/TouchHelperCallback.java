package com.lovejjfg.blogdemo.recyclerview;

import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


/**
 * Created by Joe on 2016-03-28
 * Email: zhangjun166@pingan.com.cn
 */
public class TouchHelperCallback extends ItemTouchHelper.Callback {
//        int swipeFlags =  ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        return makeMovementFlags(0, swipeFlags);
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT);
    }
    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (callBack != null) {
            callBack.onItemMove(viewHolder.getAdapterPosition(),
                    target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (callBack != null) {
            callBack.onItemDismiss(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        getDefaultUIUtil().clearView(((MoreFangshiHolder) viewHolder).mDelete);
    }

    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//        if (viewHolder != null) {
//            getDefaultUIUtil().onSelected(((MoreFangshiHolder) viewHolder).mDelete);
//        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                            boolean isCurrentlyActive) {
//        getDefaultUIUtil().onDraw(c, recyclerView,
//                ((MoreFangshiHolder)(viewHolder)).mDelete, dX, dY,
//                actionState, isCurrentlyActive);
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

//    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
//                                RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
//                                boolean isCurrentlyActive) {
//        getDefaultUIUtil().onDrawOver(c, recyclerView,
//                view, dX, dY,
//                actionState, isCurrentlyActive);
//        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//    }


//    @Override
//    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//            View view = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.item_delete, recyclerView, false);
//            getDefaultUIUtil().onDraw(c, recyclerView, view, dX, dY, actionState, isCurrentlyActive);
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
////            View itemView = viewHolder.itemView;
////
////            Paint paint = new Paint();
////            Bitmap bitmap;
////
////            if (dX > 0) { // swiping right
////                paint.setColor(recyclerView.getContext().getResources().getColor(R.color.accent_material_light));
////                bitmap = BitmapFactory.decodeResource(recyclerView.getContext().getResources(), R.mipmap.icon_end);
////                float height = (itemView.getHeight() / 2) - (bitmap.getHeight() / 2);
////
////                c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom(), paint);
////                c.drawBitmap(bitmap, 96f, (float) itemView.getTop() + height, null);
////
////            } else { // swiping left
////                paint.setColor(recyclerView.getContext().getResources().getColor(R.color.colorPrimary));
////                bitmap = BitmapFactory.decodeResource(recyclerView.getContext().getResources(), R.mipmap.icon_start);
////                float height = (itemView.getHeight() / 2) - (bitmap.getHeight() / 2);
////                float bitmapWidth = bitmap.getWidth();
////
////                c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), paint);
////                c.drawBitmap(bitmap, ((float) itemView.getRight() - bitmapWidth) - 96f, (float) itemView.getTop() + height, null);
////            }
////            getDefaultUIUtil().onDrawOver(c, recyclerView, ((RemovableViewHolder) viewHolder).getSwipableView(), dX, dY,    actionState, isCurrentlyActive);
////            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//
//
//        }
//        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
//            Log.e("DYY", dY + "");
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        }
//    }


    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public void setItemTouchCallBack(@Nullable ItemTouchCallBack callBack) {
        this.callBack = callBack;
    }

    @Nullable
    private ItemTouchCallBack callBack;

    public interface ItemTouchCallBack {
        void onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }
}
