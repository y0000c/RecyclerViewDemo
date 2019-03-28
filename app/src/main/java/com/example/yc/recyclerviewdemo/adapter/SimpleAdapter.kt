package com.example.yc.recyclerviewdemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.yc.recyclerviewdemo.R
import com.example.yc.recyclerviewdemo.inface.OnItemClickListener

/**
 * 普通适配器
 */
class SimpleAdapter(var context: Context, var _datas: List<String>, var resId: Int) : RecyclerView.Adapter<SimpleAdapter.MViewHolder>() {

    var itemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(resId, parent, false)

        val holder = MViewHolder(view)
        if (itemClickListener != null) {
            view.setOnClickListener {
                itemClickListener!!.onItemClick(view, holder.adapterPosition)
            }
        }

        return holder
    }

    override fun getItemCount(): Int {
        return _datas.size
    }

    override fun onBindViewHolder(holder: MViewHolder?, position: Int) {
        holder?.nameTv?.text = _datas[position]
    }

    inner class MViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var nameTv = itemView.findViewById<TextView>(R.id.item_text)
    }


}