package com.example.yc.recyclerviewdemo.activity_touch

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.yc.recyclerviewdemo.R
import com.example.yc.recyclerviewdemo.inface.OnItemClickListener
import com.example.yc.recyclerviewdemo.inface.OnItemLongPressListener
import com.example.yc.recyclerviewdemo.touchhelper.SimpleTouchCallBack
import kotlinx.android.synthetic.main.activity_uc.*

/**
 * 目前只是模拟了UC频道的案例样式与基本功能
 * 某些动画效果没有实现，如全局拖动，删除和添加时，是直接移动，不是"凭空消失，凭空出现"
 * （猜想：要实现上述效果，是利用单个adapter实现。删除或者添加时，是利用）
 *
 */
class 仿UC频道选择 : AppCompatActivity() {

    var mSelectedList = arrayListOf<String>()
    var mUnSelectList = arrayListOf<String>()

    var mSelectedAdapter: UCAdapter? = null
    var mUnSelectAdapter: UCAdapter? = null

    var mineTouchCallback: SimpleTouchCallBack? = null
    var mineTouchHelper : ItemTouchHelper? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uc)
        initData()
        initAdapter()
        initListener()

        mine_recyclerView.layoutManager = GridLayoutManager(this, 4)
        mine_recyclerView.adapter = mSelectedAdapter

        recommend_recyclerView.layoutManager = GridLayoutManager(this, 4)
        recommend_recyclerView.adapter = mUnSelectAdapter

        mineTouchCallback = SimpleTouchCallBack(mSelectedList,mSelectedAdapter
                as RecyclerView.Adapter<*>)
        mineTouchCallback!!.isLongPressDragEnable = false
        mineTouchCallback!!.initBackRes = R.drawable.item_uc
        mineTouchHelper = ItemTouchHelper(mineTouchCallback)
        mineTouchHelper!!.attachToRecyclerView(mine_recyclerView)
    }


    /**
     * 初始化假数据
     */
    private fun initData() {
        for (item in 1..9)
            mSelectedList.add(item.toString().plus(item).plus(item))

        for (item in 'A'..'I')
            mUnSelectList.add(item.toString().plus(item).plus(item))
    }

    /**
     * 初始化构造器
     */
    private fun initAdapter() {
        mSelectedAdapter = UCAdapter(mSelectedList, this,
                isShowDeleteIcon = false, isMineLayout = true)

        mUnSelectAdapter = UCAdapter(mUnSelectList, this,
                isShowDeleteIcon = false, isMineLayout = false)
    }

    private fun initListener() {
        /**
         * 我的频道点击事件
         */
        mSelectedAdapter!!.listener = object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                if (mSelectedAdapter!!.isShowDeleteIcon) // 如果显示了删除icon
                {
                    val item = mSelectedList[position]
                    mUnSelectAdapter!!.add(item)
                    mSelectedAdapter!!.removeAt(position)
                }
            }
        }
        /**
         * 我的频道长按事件
         */
        mSelectedAdapter!!.longListener = object :OnItemLongPressListener{
            override fun onItemLongPress(viewHolder: RecyclerView.ViewHolder, position: Int) {
                if(!mSelectedAdapter!!.isShowDeleteIcon){ // 未显示删除icon,长按打开
                    mineTouchCallback!!.isLongPressDragEnable = true //
                    mineTouchHelper!!.startDrag(viewHolder)
                    mSelectedAdapter!!.isShowDeleteIcon = true
                    mSelectedAdapter!!.notifyDataSetChanged()
                    edit_btn.text = "完成"
                }
            }
        }
        /**
         * 推荐频道点击事件
         */
        mUnSelectAdapter!!.listener = object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val item = mUnSelectList[position]
                mUnSelectAdapter!!.removeAt(position)
                mSelectedAdapter!!.add(item)
            }
        }



        /**
         * 编辑按钮点击事件，控制delete事件的开关
         */
        edit_btn.setOnClickListener {
            if (mSelectedAdapter!!.isShowDeleteIcon) // 已经显示了删除按钮，需要关闭
            {
                edit_btn.text = "编辑"
                mSelectedAdapter!!.isShowDeleteIcon = false
                mineTouchCallback!!.isLongPressDragEnable = false
            } else  // 未显示删除按钮，需要打开
            {
                edit_btn.text = "完成"
                mSelectedAdapter!!.isShowDeleteIcon = true
                mineTouchCallback!!.isLongPressDragEnable = true
            }
            mSelectedAdapter!!.notifyDataSetChanged()
        }
    }

    /**
     * 我的频道以及推荐频道的适配器
     */
    inner class UCAdapter : RecyclerView.Adapter<UCAdapter.UCViewHolder> {

        var mDatas: ArrayList<String>? = null
        var mContext: Context? = null
        var isShowDeleteIcon = false   // 是否显示删除按钮，用于我的频道
        var isMineLayout = false       // 是否为推荐视图的适配去

        var listener: OnItemClickListener? = null // 点击事件回调类
        var longListener :OnItemLongPressListener? = null // 长按回调事件

        constructor(mDatas: ArrayList<String>?, mContext: Context, isShowDeleteIcon: Boolean, isMineLayout: Boolean) {
            this.mContext = mContext
            this.mDatas = mDatas
            this.isMineLayout = isMineLayout
            this.isShowDeleteIcon = isShowDeleteIcon
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UCAdapter.UCViewHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_uc, parent, false)
            val mineViewHodler = UCViewHolder(view)
            if (listener != null) {
                view.setOnClickListener {
                    listener!!.onItemClick(it, mineViewHodler.adapterPosition)
                }
            }
            if(longListener != null)
            {
                view.setOnLongClickListener{
                    longListener!!.onItemLongPress(mineViewHodler, mineViewHodler.adapterPosition)
                    true
                }
            }
            return mineViewHodler
        }

        override fun getItemCount(): Int {
            if (mDatas == null)
                return 0
            return mDatas!!.size
        }

        override fun onBindViewHolder(holder: UCAdapter.UCViewHolder, position: Int) {
            holder.title.text = mDatas!![position]
            if (isMineLayout) // 我的频道
            {
                holder.icon.setImageResource(android.R.drawable.ic_delete)
                if (isShowDeleteIcon) // 是否需要显示删除icon
                    holder.icon.visibility = View.VISIBLE
                else
                    holder.icon.visibility = View.GONE
            } else // 推荐频道
            {
                holder.icon.visibility = View.VISIBLE
                holder.icon.setImageResource(android.R.drawable.ic_input_add)
            }
        }

        /**
         * 删除一个元素
         */
        public fun removeAt(index: Int) {
            mDatas!!.removeAt(index)
            notifyItemRemoved(index)
        }

        public fun add(item: String) {
            mDatas!!.add(item)
            notifyItemInserted(mDatas!!.size - 1)
        }

        inner class UCViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var title = view.findViewById<TextView>(R.id.item_uc_title)!!
            var icon = view.findViewById<ImageView>(R.id.item_uc_image)!!
        }
    }
}