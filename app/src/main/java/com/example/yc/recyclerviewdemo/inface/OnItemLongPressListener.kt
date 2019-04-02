package com.example.yc.recyclerviewdemo.inface

import android.support.v7.widget.RecyclerView

/**
 * RecyclerView 长按回调接口
 */
interface OnItemLongPressListener {
    fun onItemLongPress(viewHolder: RecyclerView.ViewHolder)
}