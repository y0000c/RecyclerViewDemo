package com.example.yc.recyclerviewdemo

object CommonUtil {

    fun getDatas(): ArrayList<String> {
        val result = arrayListOf<String>()
        for (item in 1..20)
            result.add(item.toString().plus(item).plus(item).plus(item))
        return result
    }
}