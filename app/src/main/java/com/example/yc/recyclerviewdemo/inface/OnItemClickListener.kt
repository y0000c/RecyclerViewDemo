package com.example.yc.recyclerviewdemo.inface

import android.view.View

/**
 * RecyclerView 点击事件回调接口
 */
interface OnItemClickListener {
    fun onItemClick(view: View,position:Int)
}