package com.example.yc.recyclerviewdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.yc.recyclerviewdemo.adapter.SimpleAdapter
import com.example.yc.recyclerviewdemo.touchhelper.SimpleTouchCallBack
import kotlinx.android.synthetic.main.activity_empty_recyclerview.*

class TouchActivity : AppCompatActivity() {

    var _datas = CommonUtil.getDatas()
    var _adapter: SimpleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_recyclerview)

        _adapter = SimpleAdapter(this, _datas)
        main_recyclerView.adapter = _adapter
        main_recyclerView.layoutManager = LinearLayoutManager(this)
        ItemTouchHelper(SimpleTouchCallBack(_datas, _adapter!!
                as RecyclerView.Adapter<RecyclerView.ViewHolder>))
                .attachToRecyclerView(main_recyclerView)   // 添加滑动，拖动事件

    }
}
