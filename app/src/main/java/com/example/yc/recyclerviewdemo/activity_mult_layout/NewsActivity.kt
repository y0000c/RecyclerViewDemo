package com.example.yc.recyclerviewdemo.activity_mult_layout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.yc.recyclerviewdemo.R
import com.example.yc.recyclerviewdemo.adapter.NewsAdapter
import com.example.yc.recyclerviewdemo.entity.News
import kotlinx.android.synthetic.main.activity_empty_recyclerview.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 新闻列表多布局显示
 */
class NewsActivity : AppCompatActivity() {

    // 数据
    var mDatas = arrayListOf<News>()

    // 适配器
    var mAdapter: NewsAdapter? = null

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
        for (item in 1..20) {
            // 图片数量是0-4张
            mDatas.add(News((Math.random()*5).toInt(),"这个是新闻标题",
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                            Locale.getDefault()).format(Date())))
        }
        mAdapter = NewsAdapter(this,mDatas)
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        main_recyclerView.layoutManager = LinearLayoutManager(this)
        main_recyclerView.adapter = mAdapter
    }
}
