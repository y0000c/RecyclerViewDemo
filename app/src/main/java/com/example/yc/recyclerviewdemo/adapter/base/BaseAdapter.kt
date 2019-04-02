package com.example.yc.recyclerviewdemo.adapter.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yc.recyclerviewdemo.inface.OnItemClickListener
import com.example.yc.recyclerviewdemo.inface.OnItemLongPressListener

/**
 * author: HYC
 * date: 2019/4/2 15:13
 * description:
 * 封装了基类适配器，适用于单布局
 * 支持JavaBean泛型
 * 支持点击，长按事件
 * 支持添加数据，更新数据'
 * 子类适配器，复写getLayoutId和bindViewData即可
*/
abstract class BaseAdapter<T>(var mDatas: MutableList<T>, var mContext: Context) : RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder>() {

    /**
     * item点击事件
     */
    var itemClick: OnItemClickListener? = null

    /**
     * 长按事件
     */
    var longClick: OnItemLongPressListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (getLayoutId() <= 0)
            throw Exception("请返回有效的布局id")
        val view = LayoutInflater.from(mContext).inflate(getLayoutId(), parent, false)
        val holder = BaseViewHolder(view)
        // 设置点击事件
        initListener(view, holder)
        return holder
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bindViewData(holder, mDatas)
    }


    /**
     * 初始化点击事件
     */
    private fun initListener(view: View, holder: BaseViewHolder) {
        // 点击事件
        view.setOnClickListener {
            itemClick?.onItemClick(it, holder.adapterPosition)
        }

        // 长按事件
        view.setOnLongClickListener {
            longClick?.onItemLongPress(holder)
            true
        }
    }

    /**
     * 刷新数据
     */
    fun refreshDatas(newDatas:MutableList<T>)
    {
        mDatas.clear()
        mDatas.addAll(newDatas)
        notifyDataSetChanged()
    }

    /**
     * 添加数据
     */
    fun addDatas(newDatas:MutableList<T>)
    {
        val size = mDatas.size
        mDatas.addAll(newDatas)
        notifyItemRangeInserted(size,newDatas.size)
    }
    
    /**
     * 设置点击事件
     */
    fun setOnItemClickListener(itemClick: OnItemClickListener) = { this.itemClick = itemClick }

    /**
     * 设置长按事件
     */
    fun setOnLongClickListener(longClick:OnItemLongPressListener) = {this.longClick = longClick}

    /**
     * 抽象方法，返回资源id
     */
    abstract fun getLayoutId(): Int

    /**
     * 抽象方法，绑定视图和数据
     */
    abstract fun bindViewData(holder: BaseViewHolder, mDatas: List<T>)

    /**
     * 缓存类
     */
    inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val crashViews = SparseArray<View>()

        /**
         * 获取缓存的子控件
         */
        fun <V :View> getView(viewId: Int): V {
            var subView = crashViews[viewId]
            if (subView == null) {
                subView = itemView.findViewById<V>(viewId)
                crashViews.put(viewId, subView)
            }
            return subView as V
        }
    }
}