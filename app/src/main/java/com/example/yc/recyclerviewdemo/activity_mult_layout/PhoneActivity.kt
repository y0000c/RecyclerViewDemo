package com.example.yc.recyclerviewdemo.activity_mult_layout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.yc.recyclerviewdemo.R
import com.example.yc.recyclerviewdemo.adapter.PhoneAdapter
import com.example.yc.recyclerviewdemo.entity.Phone
import kotlinx.android.synthetic.main.activity_empty_recyclerview.*

class PhoneActivity : AppCompatActivity() {

    // 数据
    var mDatas = arrayListOf<Phone>()

    // 适配器
    var mAdapter: PhoneAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_recyclerview)
        initData()
        initView()
    }

    /**
     * 初始化临时数据
     */
    private fun initData() {
        val tags = arrayOf("A", "B", "C", "D")
        for (item in tags) {
            mDatas.add(Phone(true, item))
            mDatas.add(Phone(false, item.plus("猫")))
            mDatas.add(Phone(false, item.plus("狗")))
            mDatas.add(Phone(false, item.plus("猪")))
            mDatas.add(Phone(false, item.plus("牛")))
        }
        mAdapter = PhoneAdapter(this,mDatas)
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        main_recyclerView.layoutManager = LinearLayoutManager(this)
        main_recyclerView.adapter = mAdapter
    }
}
