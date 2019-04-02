package com.example.yc.recyclerviewdemo.itemline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

/**
 * author: HYC
 * date: 2019/4/2 16:24
 * description: 线性布局使用的Item分割线
 */
class LinearItemLine(var context: Context) : RecyclerView.ItemDecoration() {

    var divider: Drawable? = null  // 需要绘制的分割线样式
    var width = 5                   // 分割线宽度

    init {
       divider = ColorDrawable(Color.GRAY)
    }

    /**
     * 绘制了itemView以后再绘制分割线
     */
    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(c, parent, state)
        val layout = parent?.layoutManager
        if (layout is GridLayoutManager || layout is StaggeredGridLayoutManager)
            throw Exception("请选择正确的布局类型")
        layout as LinearLayoutManager
        if (layout.orientation == LinearLayoutManager.HORIZONTAL) // 水平滚动
            drawVerticalLine(c, parent, state) // 绘制垂直方向分割线
        else
            drawHorizontalLine(c, parent, state) // 绘制水平方向分割线
    }

    /**
     * 绘制水平方向的分割线
     */
    private fun drawHorizontalLine(c: Canvas?, parent: RecyclerView, state: RecyclerView.State?) {
        // 水平方向的分割线，长度和RecyclerView保持一致，宽度 = width
        // 但是这里使用的是坐标计算，因此是left,top,right,bottom

        // 左右是跟父布局保持一致，不随itemView的position改变
        val left = (parent.left + parent.paddingLeft)
        val right = (parent.right - parent.paddingRight)

        // top，bottom是跟随着itemView的positon不断改变的
        var top = 0
        var bottom = 0

        // 遍历每一个ItemView,通过他们当前的bottom,marginBottom来计算分割线的top
        // 分割线的bottom = top + width
        for (item in 0 until parent.childCount) {
            val itemView = parent.getChildAt(item)
            val params = itemView.layoutParams as RecyclerView.LayoutParams
            top = params.bottomMargin + itemView.bottom
            bottom = top + width
            divider?.bounds = Rect(left, top, right, bottom)
            divider?.draw(c)
        }
    }

    /**
     * 绘制垂直方向分割线
     */
    private fun drawVerticalLine(c: Canvas?, parent: RecyclerView, state: RecyclerView.State?) {
        // 垂直方向的分割线，宽度和RecyclerView保持一致,长度 = width
        // 同时是通过left,top,right,bottom计算

        // 宽度坐标是和父布局保持一致
        val top = parent.top + parent.paddingTop
        val bottom = parent.bottom - parent.paddingBottom

        // 长度坐标随ItemView而改变
        var left = 0
        var right = 0
        for (item in 0 until parent.childCount) {
            val itemView = parent.getChildAt(item)
            val params = itemView.layoutParams as RecyclerView.LayoutParams
            left = params.marginEnd + itemView.right
            right = left + width
            divider?.bounds = Rect(left, top, right, bottom)
            divider?.draw(c)
        }

    }

    /**
     * 计算当前ItemView距离上一个ItemView的偏移量
     */
    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val layout = parent?.layoutManager as LinearLayoutManager
        if (layout.orientation == LinearLayoutManager.HORIZONTAL) // 水平滚动
        {
            // 垂直分割线，ItemView出现向右偏移
            outRect?.set(0, 0, width, 0)
        } else  // 垂直滚动
        {
            // 水平方向分割线，ItemView会出现向下偏移
            outRect?.set(0, 0, 0, width)
        }
    }

    /**
     * 利用构造者模式，创建ItemLine
     */
    class Builder(val context: Context) {
        var divider: Drawable? = null
        var width = 5

        /**
         * 设置颜色
         */
        fun setColor(color: Int): Builder {
            divider = ColorDrawable(color)
            return this
        }

        /**
         * 设置宽度
         */
        fun setWidth(value: Int): Builder {
            width = value
            return this
        }

        /**
         * 通过资源文件设置item
         */
        fun setResId(resId: Int): Builder {
            divider = ContextCompat.getDrawable(context, resId)
            width = divider!!.intrinsicWidth
            return this
        }

        /**
         * 构造出ItemLine实例对象
         */
        fun build(): LinearItemLine {
            val line = LinearItemLine(context)
            line.divider = divider
            line.width = width
            return line
        }
    }
}


//public class RecyclerViewGridItemLine extends RecyclerView.ItemDecoration {
//
//    private Drawable mDivider;
//    private boolean mShowLastLine;
//    private int mHorizonSpan;
//    private int mVerticalSpan;
//
//    /**
//     * 通过color构建
//     *
//     * @param horizonSpan  水平线大小
//     * @param verticalSpan 垂直线大小
//     * @param divider      分割线
//     * @param showLastLine 是否显示最后一行
//     */
//    private RecyclerViewGridItemLine(int horizonSpan, int verticalSpan, Drawable divider, boolean showLastLine) {
//        this.mHorizonSpan = horizonSpan;
//        this.mShowLastLine = showLastLine;
//        this.mVerticalSpan = verticalSpan;
//        mDivider = divider;
//    }
//
//    @Override
//    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        drawHorizontal(c, parent);
//        drawVertical(c, parent);
//    }
//
//    private void drawHorizontal(Canvas c, RecyclerView parent) {
//        int childCount = parent.getChildCount();
//
//        for (int i = 0; i < childCount; i++) {
//        View child = parent.getChildAt(i);
//
//        //最后一行 && 横线不绘制
//        if (isLastRaw(parent, i, getSpanCount(parent), childCount) && !mShowLastLine) {
//            continue;
//        }
//        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//        final int left = child.getLeft() - params.leftMargin;
//        final int right = child.getRight() + params.rightMargin;
//        final int top = child.getBottom() + params.bottomMargin;
//        final int bottom = top + mHorizonSpan;
//
//        mDivider.setBounds(left, top, right, bottom);
//        mDivider.draw(c);
//    }
//    }
//
//    private void drawVertical(Canvas c, RecyclerView parent) {
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//        final View child = parent.getChildAt(i);
//        // 当前itemView表示最后一列
//        if ((parent.getChildViewHolder(child).getAdapterPosition() + 1) % getSpanCount(parent) == 0) {
//            continue;
//        }
//        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//        final int top = child.getTop() - params.topMargin;
//        // +mHorizonSpan是为了封闭交叉点。也可以添加在drawableHorizontal中的right。
//        final int bottom = child.getBottom() + params.bottomMargin + mHorizonSpan;
//        final int left = child.getRight() + params.rightMargin;
//        int right = left + mVerticalSpan;
//        mDivider.setBounds(left, top, right, bottom);
//        mDivider.draw(c);
//    }
//    }
//
//    /**
//     * 计算偏移量
//     */
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        int spanCount = getSpanCount(parent); // 获取列数
//        int childCount = parent.getAdapter().getItemCount(); // itemView总数
//        // 当前的itemView的position
//        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
//
//        if (((itemPosition % spanCount) + 1) == spanCount) // 最后一列，不绘制右边,只绘制下面
//        {
//            outRect.set(0,0,0,mHorizonSpan);
//        } else // 最后一行,以及中间的内容，都要绘制下面和右边
//        {
//            outRect.set(0, 0, mVerticalSpan, mHorizonSpan);
//        }
//        // 如果最后一行不绘制下面，只绘制右边，则添加分支
////        if (isLastRaw(parent, itemPosition, spanCount, childCount)){
////            outRect.set(0,0,mVerticalSpan,0);
////        }
//    }
//
//    /**
//     * 获取列数
//     */
//    private int getSpanCount(RecyclerView parent) {
//        // 列数
//        int mSpanCount = -1;
//        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
//        if (layoutManager instanceof GridLayoutManager) {
//            mSpanCount = ((GridLayoutManager) layoutManager).getSpanCount();
//        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
//            mSpanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
//        }
//        return mSpanCount;
//    }
//
//    /**
//     * 是否最后一行
//     *
//     * @param parent     RecyclerView
//     * @param pos        当前item的位置
//     * @param spanCount  每行显示的item个数
//     * @param childCount child个数
//     */
//    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
//        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
//
//        if (layoutManager instanceof GridLayoutManager) {
//            return getResult(pos, spanCount, childCount);
//        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
//            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
//            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
//                // StaggeredGridLayoutManager 且纵向滚动
//                return getResult(pos, spanCount, childCount);
//            } else {
//                // StaggeredGridLayoutManager 且横向滚动
//                if ((pos + 1) % spanCount == 0) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    private boolean getResult(int pos, int spanCount, int childCount) {
//        int remainCount = childCount % spanCount;//获取余数
//        //如果正好最后一行完整;
//        if (remainCount == 0) { //
//            if (pos >= childCount - spanCount) {
//                return true; //最后一行全部不绘制;
//            }
//        } else {
//            if (pos >= childCount - childCount % spanCount) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 使用Builder构造
//     */
//    public static class Builder {
//        private Context mContext;
//        private Resources mResources;
//        private boolean mShowLastLine;
//        private int mHorizonSpan;
//        private int mVerticalSpan;
//        private Drawable mDivider;
//
//        public Builder(Context context) {
//            mContext = context;
//            mResources = context.getResources();
//            mShowLastLine = true;
//            mHorizonSpan = 0;
//            mVerticalSpan = 0;
//            mDivider = new ColorDrawable(Color.GRAY);
//        }
//
//        /**
//         * 通过资源文件设置分隔线颜色
//         */
//        public Builder setColorResource(@ColorRes int resource) {
//            setColor(ContextCompat.getColor(mContext, resource));
//            return this;
//        }
//
//        /**
//         * 通过drawable设置分割线样式
//         *
//         * @return this
//         */
//        public Builder setDrawableResouce(@DrawableRes int drawableId) {
//            mDivider = ContextCompat.getDrawable(mContext, drawableId);
//            mHorizonSpan = mDivider.getIntrinsicHeight();
//            mVerticalSpan = mHorizonSpan;
//            return this;
//        }
//
//        /**
//         * 设置颜色
//         */
//        public Builder setColor(@ColorInt int color) {
//            mDivider = new ColorDrawable(color);
//            return this;
//        }
//
//        /**
//         * 通过dp设置垂直间距
//         */
//        public Builder setVerticalSpan(@DimenRes int vertical) {
//            this.mVerticalSpan = mResources.getDimensionPixelSize(vertical);
//            return this;
//        }
//
//        /**
//         * 通过px设置垂直间距
//         */
//        public Builder setVerticalSpan(float mVertical) {
//            this.mVerticalSpan = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mVertical, mResources.getDisplayMetrics());
//            return this;
//        }
//
//        /**
//         * 通过dp设置水平间距
//         */
//        public Builder setHorizontalSpan(@DimenRes int horizontal) {
//            this.mHorizonSpan = mResources.getDimensionPixelSize(horizontal);
//            return this;
//        }
//
//        /**
//         * 通过px设置水平间距
//         */
//        public Builder setHorizontalSpan(float horizontal) {
//            this.mHorizonSpan = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, horizontal, mResources.getDisplayMetrics());
//            return this;
//        }
//
//        /**
//         * 是否最后一条显示分割线
//         */
//        public RecyclerViewGridItemLine.Builder setShowLastLine(boolean show) {
//            mShowLastLine = show;
//            return this;
//        }
//
//        public RecyclerViewGridItemLine build() {
//            return new RecyclerViewGridItemLine(mHorizonSpan, mVerticalSpan, mDivider, mShowLastLine);
//        }
//    }
