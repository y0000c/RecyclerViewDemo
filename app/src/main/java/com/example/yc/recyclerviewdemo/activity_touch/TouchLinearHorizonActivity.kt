package com.example.yc.recyclerviewdemo.activity_touch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.yc.recyclerviewdemo.R
import com.example.yc.recyclerviewdemo.adapter.CommonAdapter
import com.example.yc.recyclerviewdemo.touchhelper.SimpleTouchCallBack
import com.example.yc.recyclerviewdemo.util.CommonUtil
import kotlinx.android.synthetic.main.activity_empty_recyclerview.*

/**
 * 横向滚动的List
 */
class TouchLinearHorizonActivity : AppCompatActivity() {

    var _datas = CommonUtil.getDatas()
    var _adapter: CommonAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.yc.recyclerviewdemo.R.layout.activity_empty_recyclerview)

        _adapter = CommonAdapter(this, _datas,R.layout.item_linear_horizon)
        main_recyclerView.adapter = _adapter
        main_recyclerView.layoutManager = LinearLayoutManager(this
                ,LinearLayoutManager.HORIZONTAL,false)
        ItemTouchHelper(SimpleTouchCallBack(_datas, _adapter!!
                as RecyclerView.Adapter<*>))
                .attachToRecyclerView(main_recyclerView)   // 添加滑动，拖动事件

    }
}
