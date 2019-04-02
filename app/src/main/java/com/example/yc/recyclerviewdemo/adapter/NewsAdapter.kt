package com.example.yc.recyclerviewdemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.yc.recyclerviewdemo.R
import com.example.yc.recyclerviewdemo.entity.News

/**
 * @author:    YC
 * @date:    2019/4/1 0001
 * @time:    21:50
 *@detail:
 */
/**
 * 新闻列表，多布局
 */
class NewsAdapter(var context: Context, var _datas: List<News>)
    : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    val NONE = 0 // 无图片
    val ONE = 1 // 1图片
    val MUT = 2 // 2以上称为多图片


    /**
     * 根据不同的类型，来加载不同的布局，实现多布局
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            NewsViewHolder {
        var view: View? = null
        view = if (viewType == NONE) {
            LayoutInflater.from(context)
                    .inflate(R.layout.item_news_none, parent, false)
        } else if (viewType == ONE) {
            LayoutInflater.from(context)
                    .inflate(R.layout.item_news_one, parent, false)
        } else {
            LayoutInflater.from(context)
                    .inflate(R.layout.item_news_mul, parent, false)
        }
        return NewsViewHolder(view!!)
    }

    /**
     *   多布局必须要实现该方法，根据某个变量值，返回不同的类型。
     *
     */
    override fun getItemViewType(position: Int): Int {
        val size = _datas[position].imageSize
        return when (size) {
            0 -> NONE
            1 -> ONE
            else -> MUT
        }
    }

    override fun getItemCount(): Int {
        return _datas.size
    }

    /**
     * 根据不同的类型，绑定不同布局中子控件的值
     */
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        // 这里省略了设置图片的逻辑
        // 如何进一步简化代码-》将不同布局中的title,time对应的TextView都设置成同样的id
        // 这样就不需要对title,事件分类型判断了。只需要对图片分类型即可
        when {
            holder.itemViewType == NONE -> {
                holder.setText(R.id.item_news_none_title, _datas[position].name)
                holder.setText(R.id.item_news_none_time, _datas[position].timeStr)
            }
            holder.itemViewType == ONE -> {
                holder.setText(R.id.item_news_one_title, _datas[position].name)
                holder.setText(R.id.item_news_one_time, _datas[position].timeStr)
            }
            else -> {
                holder.setText(R.id.item_news_mul_title, _datas[position].name)
                holder.setText(R.id.item_news_mul_time, _datas[position].timeStr)
                if(_datas[position].imageSize == 2)
                    holder.getView(R.id.item_news_mul_img3).visibility = View.GONE
                else
                    holder.getView(R.id.item_news_mul_img3).visibility = View.VISIBLE
            }
        }
    }

    /**
     * 缓存类，不能和普通单布局一样，直接findViewById固定子控件
     * 需要利用缓存集合来实现不同布局各自加载子控件的方法
     */
    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val crashViews = SparseArray<View>()

        /**
         * 根据id获取view控件
         */
        fun getView(id: Int): View {
            var view = crashViews[id]
            if (view == null) {
                view = itemView.findViewById<View>(id)
                crashViews.put(id, view)
            }
            return view
        }

        /**
         * 设置文本信息
         */
        fun setText(id: Int, value: String) {
            val view = getView(id)
            if (view is TextView)
                view.text = value
        }
    }
}
