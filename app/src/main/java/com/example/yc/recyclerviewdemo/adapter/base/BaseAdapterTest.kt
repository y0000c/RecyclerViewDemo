package com.example.yc.recyclerviewdemo.adapter.base

import android.content.Context
import android.widget.TextView
import com.example.yc.recyclerviewdemo.R

/**
 * author: HYC
 * date: 2019/4/2 15:43
 * description: 测试单布局基类使用
*/
class BaseAdapterTest<String>(mData:MutableList<String>,mContext:Context)
    :BaseAdapter<String>(mData,mContext){
    override fun getLayoutId(): Int {
        return R.layout.item_linear_vertical
    }

    override fun bindViewData(holder: BaseViewHolder, mDatas: List<String>) {
        holder.getView<TextView>(R.id.item_text).text = mDatas[holder.adapterPosition].toString()
    }
}