package com.example.yc.recyclerviewdemo

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.yc.recyclerviewdemo.adapter.SimpleAdapter
import com.example.yc.recyclerviewdemo.inface.OnItemClickListener
import kotlinx.android.synthetic.main.activity_empty_recyclerview.*


/**
 * 显示所有的Activity
 */
class MainActivity : AppCompatActivity() {

    var _datas = arrayListOf<String>()
    var _adapter: SimpleAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_recyclerview)
        initDatas()
        initAdapter()
        main_recyclerView.layoutManager = LinearLayoutManager(this) // 设置布局类型
        main_recyclerView.adapter = _adapter  // 设置适配器

    }

    /**
     * 获取所有的Activity名称数据
     */
    private fun initDatas() {

        val packageInfo = packageManager.getPackageInfo(
                packageName, PackageManager.GET_ACTIVITIES)

        for (activity in packageInfo.activities) {
            if (activity.name == MainActivity::class.java.name)
                continue
            _datas.add(activity.name)
        }
    }

    /**
     * 初始化适配器
     */
    private fun initAdapter() {
        _adapter = SimpleAdapter(this, _datas, R.layout.item_linear_vertical)
        _adapter!!.itemClickListener = object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent()
                intent.setClassName(this@MainActivity, _datas[position])
                this@MainActivity.startActivity(intent)
            }
        }
    }


}

