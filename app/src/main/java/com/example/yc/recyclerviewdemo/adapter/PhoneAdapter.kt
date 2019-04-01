package com.example.yc.recyclerviewdemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.yc.recyclerviewdemo.R
import com.example.yc.recyclerviewdemo.entity.Phone

/**
 * @author:    YC
 * @date:    2019/4/1 0001
 * @time:    21:50
 *@detail:
 */
/**
 * 手机联系人适配器，多布局
 */
class PhoneAdapter(var context: Context, var _datas: List<Phone>)
    : RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder>() {


    val TAG = 0 // 标签
    val COM = 1 // 普通名称


    /**
     * 根据不同的类型，来加载不同的布局，实现多布局
     */
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):
            PhoneViewHolder {
        var view: View? = null
        view = if (viewType == TAG) {
            LayoutInflater.from(context)
                    .inflate(R.layout.item_phone_tag, parent, false)
        } else {
            LayoutInflater.from(context)
                    .inflate(R.layout.item_phone_com, parent, false)
        }
        return PhoneViewHolder(view!!)
    }

    /**
     *   多布局必须要实现该方法，根据某个变量值，返回不同的类型。
     *   isTag 返回0
     */
    override fun getItemViewType(position: Int): Int {
        return if (_datas[position].isTag) TAG else COM
    }

    override fun getItemCount(): Int {
        return _datas.size
    }

    /**
     * 根据不同的类型，绑定不同布局中子控件的值
     */
    override fun onBindViewHolder(holder: PhoneViewHolder?, position: Int) {
        if (holder!!.itemViewType == TAG)
            holder.setText(R.id.item_phone_tag, _datas[position].name)
        else
            holder.setText(R.id.item_phone_comm_name, _datas[position].name)
    }

    /**
     * 缓存类，不能和普通单布局一样，直接findViewById固定子控件
     * 需要利用缓存集合来实现不同布局各自加载子控件的方法
     */
    inner class PhoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
