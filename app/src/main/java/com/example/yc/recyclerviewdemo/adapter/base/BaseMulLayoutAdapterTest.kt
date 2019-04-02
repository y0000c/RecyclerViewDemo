package com.example.yc.recyclerviewdemo.adapter.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.yc.recyclerviewdemo.R
import com.example.yc.recyclerviewdemo.entity.News

/**
 * author: HYC
 * date: 2019/4/2 15:46
 * description: 测试多布局基类的使用
 */
class BaseMulLayoutAdapterTest(mData: MutableList<News>, mContext: Context)
    : BaseMulLayoutAdapter<News>(mData, mContext) {

    val NONE = 0 // 无图片
    val ONE = 1 // 1图片
    val MUT = 2 // 2以上称为多图片

    /**
     * 返回当前position对应的viewtype
     */
    override fun getType(mDatas: MutableList<News>, position: Int): Int {
        val size = mDatas[position].imageSize
        return when (size) {
            0 -> NONE
            1 -> ONE
            else -> MUT
        }
    }

    /**
     * 获取每个类型对应的View布局
     */
    @SuppressLint("UseSparseArrays")
    override fun getLayoutId(): SparseArray<Int> {
        return SparseArray<Int>().also {
            it.put(NONE, R.layout.item_news_none)
            it.put(ONE, R.layout.item_news_one)
            it.put(MUT, R.layout.item_news_mul)
        }
    }

    /**
     * 绑定视图数据
     */
    override fun bindViewData(holder: BaseViewHolder, mDatas: List<News>) {
        when {
            holder.itemViewType == NONE -> {
                holder.getView<TextView>(R.id.item_news_none_title).text =
                        mDatas[holder.adapterPosition].name
                holder.getView<TextView>(R.id.item_news_none_time).text =
                        mDatas[holder.adapterPosition].timeStr
            }
            holder.itemViewType == ONE -> {
                holder.getView<TextView>(R.id.item_news_one_title).text =
                        mDatas[holder.adapterPosition].name
                holder.getView<TextView>(R.id.item_news_one_time).text =
                        mDatas[holder.adapterPosition].timeStr
            }
            else -> {
                holder.getView<TextView>(R.id.item_news_mul_title).text =
                        mDatas[holder.adapterPosition].name
                holder.getView<TextView>(R.id.item_news_mul_time).text =
                        mDatas[holder.adapterPosition].timeStr
                if (mDatas[holder.adapterPosition].imageSize == 2)
                    holder.getView<ImageView>(R.id.item_news_mul_img3).visibility = View.GONE
                else
                    holder.getView<ImageView>(R.id.item_news_mul_img3).visibility = View.VISIBLE
            }
        }
    }
}