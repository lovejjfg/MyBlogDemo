package com.lovejjfg.blogdemo.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Joe on 2016/8/27.
 * Email lovejjfg@gmail.com
 */
public class FlowLayoutManager extends RecyclerView.LayoutManager {
    public static final int DEFAULT_SPACING = 20;
    public static final int EXACT_MODE = 1;//精确的模式
    public static final int FREE_MODE = 2; //自由模式

    private static final int SPACE_NO_NEED = -1;
    private static final int SPACE_NEED = -2;
    private static final int DEFALT_COUNT = 2;

    /**
     * 最大的行数
     */
    private int mMaxLinesCount = Integer.MAX_VALUE;

    @SuppressWarnings("unused")
    public int getDefaultMode() {
        return mLayoutMode;
    }

    /**
     * you can use  {@link #EXACT_MODE}  or {@link #FREE_MODE}
     */
    @SuppressWarnings("unused")
    public void setDefaultMode(int layoutMode) {
        if (this.mLayoutMode != layoutMode) {
            this.mLayoutMode = layoutMode;
            requestLayout();
        }
    }

    private int mLayoutMode = FREE_MODE;
    private int mDefalCount = DEFALT_COUNT;
    /**
     * 横向间隔
     */
    private int mHorizontalSpacing = DEFAULT_SPACING;
    /**
     * 纵向间隔
     */
    private int mVerticalSpacing = DEFAULT_SPACING;
    /**
     * 是否需要布局，只用于第一次
     */
    boolean mNeedLayout = true;
    /**
     * 当前行已用的宽度，由子View宽度加上横向间隔
     */
    private int mUsedWidth = 0;
    /**
     * 代表每一行的集合
     */
    private final List<Line> mLines = new ArrayList<>();
    private Line mLine = null;
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int sizeWidth = View.MeasureSpec.getSize(getWidthMode()) - getPaddingRight() - getPaddingLeft();
        int sizeHeight = View.MeasureSpec.getSize(getHeightMode()) - getPaddingTop() - getPaddingBottom();
        int finalWidth = (sizeWidth - mHorizontalSpacing * ((mDefalCount - 1))) / mDefalCount;
        int modeWidth = View.MeasureSpec.getMode(getWidthMode());
        int modeHeight = View.MeasureSpec.getMode(getHeightMode());
        int offsetY = 0;
        for (int i = 0; i < getItemCount(); i++) {

            final View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            int childWidthMeasureSpec;
            //不同模式不同的测量方式
            if (mLayoutMode == EXACT_MODE) {
                childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(finalWidth, View.MeasureSpec.EXACTLY);
            } else if (mLayoutMode == FREE_MODE) {
                childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(sizeWidth, modeWidth == View.MeasureSpec.EXACTLY ? View.MeasureSpec.AT_MOST : modeWidth);
            } else {
                throw new IllegalArgumentException("There is not your specified LayoutMode!! ");
            }
            int childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(sizeHeight, modeHeight == View.MeasureSpec.EXACTLY ? View.MeasureSpec.AT_MOST : modeHeight);
            // 测量child
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            if (mLine == null) {
                mLine = new Line();
            }
            int childWidth = child.getMeasuredWidth();
            mUsedWidth += childWidth;// 增加使用的宽度
            if (mUsedWidth <= sizeWidth) {// 使用宽度小于总宽度，该child属于这一行。
                mLine.addView(child);// 添加child
                mUsedWidth += mHorizontalSpacing;// 加上间隔
                if (mUsedWidth >= sizeWidth) {// 加上间隔后如果大于等于总宽度，需要换行
                    if (!newLine()) {
                        break;
                    }
                }
            } else {// 使用宽度大于总宽度。需要换行
                if (mLine.getViewCount() == 0) {// 如果这行一个child都没有，即使占用长度超过了总长度，也要加上去，保证每行都有至少有一个child
                    mLine.addView(child);// 添加child
                    if (!newLine()) {// 换行
                        break;
                    }
                } else {// 如果该行有数据了，就直接换行
                    if (!newLine()) {// 换行
                        break;
                    }
                    // 在新的一行，不管是否超过长度，先加上去，因为这一行一个child都没有，所以必须满足每行至少有一个child
                    mLine.addView(child);
                    mUsedWidth += childWidth + mHorizontalSpacing;
                }
            }

            /*xxxxxxxxxxxxxxx*/
            View view = recycler.getViewForPosition(i);

            addView(view);

            measureChildWithMargins(view, 0, 0);

            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);

            layoutDecorated(view, 0, offsetY, width, offsetY + height);

            offsetY += height;
        }
    }

    /**
     * 新增加一行
     */
    private boolean newLine() {
        mLines.add(mLine);
        if (mLines.size() < mMaxLinesCount) {
            mLine = new Line();
            mUsedWidth = 0;
            return true;
        }
        return false;
    }

    /**
     * 代表着一行，封装了一行所占高度，该行子View的集合，以及所有View的宽度总和
     */
    class Line {
        int mWidth = 0;// 该行中所有的子View累加的宽度
        int mHeight = 0;// 该行中所有的子View中高度的那个子View的高度
        List<View> views = new ArrayList<>();

        public void addView(View view) {// 往该行中添加一个
            views.add(view);
            mWidth += view.getMeasuredWidth();
            int childHeight = view.getMeasuredHeight();
            mHeight = mHeight < childHeight ? childHeight : mHeight;//高度等于一行中最高的View
        }

        public int getViewCount() {
            return views.size();
        }

        public void layoutView(int l, int t) {// 布局
            layoutView(l, t, SPACE_NEED);
        }

        public void layoutView(int l, int t, int flag) {
            int left = l;
            int count = getViewCount();
            //总宽度
            int layoutWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            //剩余的宽度，是除了View和间隙的剩余空间
            int surplusWidth = layoutWidth - mWidth - mHorizontalSpacing * (count - 1);
            if (surplusWidth >= 0) {// 剩余空间
                // 采用float类型数据计算后四舍五入能减少int类型计算带来的误差
                int splitSpacing = flag == SPACE_NO_NEED ? 0 : (int) (surplusWidth / count + 0.5);
                for (int i = 0; i < count; i++) {
                    final View view = views.get(i);
                    int childWidth = view.getMeasuredWidth();
                    int childHeight = view.getMeasuredHeight();
                    //计算出每个View的顶点，是由最高的View和该View高度的差值除以2
                    int topOffset = (int) ((mHeight - childHeight) / 2.0 + 0.5);
                    if (topOffset < 0) {
                        topOffset = 0;
                    }
                    childWidth = childWidth + splitSpacing;
                    view.getLayoutParams().width = childWidth;
                    if (splitSpacing > 0) {//View的长度改变了，需要重新measure
                        //把剩余空间平均到每个View上
                        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(childWidth, View.MeasureSpec.EXACTLY);
                        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(childHeight, View.MeasureSpec.EXACTLY);
                        view.measure(widthMeasureSpec, heightMeasureSpec);
                    }
                    //布局View
                    view.layout(left, t + topOffset, left + childWidth, t + topOffset + childHeight);
                    left += childWidth + mHorizontalSpacing; //为下一个View的left赋值
                }
            } else {
                if (count == 1) {
                    View view = views.get(0);
                    view.layout(left, t, left + view.getMeasuredWidth(), t + view.getMeasuredHeight());
                }
            }
        }
    }

}
