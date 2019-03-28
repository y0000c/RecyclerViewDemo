package com.example.yc.recyclerviewdemo.util

object CommonUtil {

    /**
     * 获取测试数据
     */
    fun getDatas(): ArrayList<String> {
        val result = arrayListOf<String>()
        for (item in 1..40)
            result.add(item.toString().plus(item).plus(item).plus(item))
        return result
    }
}