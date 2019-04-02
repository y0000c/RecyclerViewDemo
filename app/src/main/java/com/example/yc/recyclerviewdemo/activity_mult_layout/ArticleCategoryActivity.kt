package com.example.yc.recyclerviewdemo.activity_mult_layout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.yc.recyclerviewdemo.R
import com.example.yc.recyclerviewdemo.adapter.ArticleCategoryAdapter
import com.example.yc.recyclerviewdemo.entity.ArticleCategory
import kotlinx.android.synthetic.main.activity_empty_recyclerview.*

/**
 *
 */
/** 
 * author: HYC
 * date: 2019/4/2 11:30 
 * description: 文章分类系那是（RecyclerView嵌套RecyclerView）
 * 其实该界面和PhoneActivity是基本一致的，都是由一个title和一些item组成
 * 但是为什么PhoneActivity使用的多布局实现，而这里使用RecyclerView嵌套呢？
 *
 * 1、数据源结构不一致（JavaBean），Phone是一个结构，ArticleCategory是二级结构
 *    数据源结构取决于项目开发中的json解析。
 *
 * 2、界面样式不一致。
 *    PhoneActivity中，title和它对应每个item在界面上都是可看做独立个体，没有直接联系。
 *    并且item显示的list类型
 *    （这种情况可以使用多布局或者嵌套，显示多布局更方便）
 *
 *    该界面则不一样，title和对应的每个Item都是同属于一个CardView中，不能分离的。
 *    并且item显示的grid类型
 *    （这种情况下，目前个人只能通过嵌套是实现，无法通过多布局实现）
 *
 *    问题:RecyclerView嵌套出现卡顿。
 *
*/
class ArticleCategoryActivity : AppCompatActivity() {

    // 数据
    var mDatas = arrayListOf<ArticleCategory>()

    // 适配器
    var mAdapter: ArticleCategoryAdapter? = null

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
        val tags = arrayOf("文学", "军事", "历史", "科学","社会","娱乐","动漫")
        for (item in tags) {
            val category = ArticleCategory()
            category.category = item
            category.articleList = arrayListOf(
                    ArticleCategory.Article(item.plus("11"),((Math.random()*300+0.5)).toInt()),
                    ArticleCategory.Article(item.plus("22"),((Math.random()*300+0.5)).toInt()),
                    ArticleCategory.Article(item.plus("33"),((Math.random()*300+0.5)).toInt()),
                    ArticleCategory.Article(item.plus("44"),((Math.random()*300+0.5)).toInt()),
                    ArticleCategory.Article(item.plus("55"),((Math.random()*300+0.5)).toInt()),
                    ArticleCategory.Article(item.plus("66"),((Math.random()*300+0.5)).toInt()),
                    ArticleCategory.Article(item.plus("77"),((Math.random()*300+0.5)).toInt()),
                    ArticleCategory.Article(item.plus("88"),((Math.random()*300+0.5)).toInt()),
                    ArticleCategory.Article(item.plus("88"),((Math.random()*300+0.5)).toInt()),
                    ArticleCategory.Article(item.plus("99"),((Math.random()*300+0.5)).toInt()),
                    ArticleCategory.Article(item.plus("00"),((Math.random()*300+0.5)).toInt()))
            mDatas.add(category)
        }
        mAdapter = ArticleCategoryAdapter(this,mDatas)
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        main_recyclerView.layoutManager = LinearLayoutManager(this)
        main_recyclerView.adapter = mAdapter

    }
}
