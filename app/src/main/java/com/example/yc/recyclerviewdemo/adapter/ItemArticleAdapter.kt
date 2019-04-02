package com.example.yc.recyclerviewdemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.yc.recyclerviewdemo.R
import com.example.yc.recyclerviewdemo.entity.ArticleCategory

/**
 * author: HYC
 * date: 2019/4/2 11:07
 * description: 文章类别适配中的子RecyclerView适配器
 */
class ItemArticleAdapter(var context: Context, var _data: List<ArticleCategory.Article>)
    : RecyclerView.Adapter<ItemArticleAdapter.ItemArticalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemArticalViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.item_item_article, parent, false)
        return ItemArticalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return _data.size
    }

    override fun onBindViewHolder(holder: ItemArticalViewHolder, position: Int) {
        holder.articalName.text = _data[position].name
        holder.articalNum.text = _data[position].size.toString().plus("篇")
    }


    inner class ItemArticalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var articalName = itemView.findViewById<TextView>(R.id.item_item_article_name)
        var articalNum = itemView.findViewById<TextView>(R.id.item_item_article_num)
    }
}