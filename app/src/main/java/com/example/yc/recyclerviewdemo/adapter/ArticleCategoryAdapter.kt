package com.example.yc.recyclerviewdemo.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.yc.recyclerviewdemo.R
import com.example.yc.recyclerviewdemo.entity.ArticleCategory

/**
 * author: HYC
 * date: 2019/4/2 10:57
 * description: 文章类别适配器,RecyclerView嵌套RecyclerView
*/

class ArticleCategoryAdapter(var context: Context, var _datas: List<ArticleCategory>)
    : RecyclerView.Adapter<ArticleCategoryAdapter.ArticleCategoryViewHolder>() {

    /**
     * 根据不同的类型，来加载不同的布局，实现多布局
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ArticleCategoryAdapter.ArticleCategoryViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.item_artical_category, parent, false)
        return ArticleCategoryViewHolder(view)
    }


    override fun getItemCount(): Int {
        return _datas.size
    }

    /**
     * 根据不同的类型，绑定不同布局中子控件的值
     */
    override fun onBindViewHolder(holder: ArticleCategoryAdapter.ArticleCategoryViewHolder, position: Int) {
        holder.category.text = _datas[position].category
        holder.itemRecyclerView.layoutManager = GridLayoutManager(context,3)
        holder.itemRecyclerView.adapter = ItemArticleAdapter(context,_datas[position].articleList!!)
    }

    /**
     * 缓存类，不能和普通单布局一样，直接findViewById固定子控件
     * 需要利用缓存集合来实现不同布局各自加载子控件的方法
     */
    inner class ArticleCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category = itemView.findViewById<TextView>(R.id.item_artical_categoty_title)
        var itemRecyclerView = itemView.findViewById<RecyclerView>(R.id.item_artical_categoty_recyclerview)
    }


}
