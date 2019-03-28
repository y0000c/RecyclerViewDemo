package com.example.yc.recyclerviewdemo.touchhelper

import android.graphics.Canvas
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import java.util.*

/**
 * 拖动回调事件
 */
class SimpleTouchCallBack(var _datas: ArrayList<String>,
                          var _adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>)
    : ItemTouchHelper.Callback() {

    var isNeedToSwipe = false      // 是否允许左右滑动
    var isLeftRightSwipe = false    // 是否是左右滑动
    var isLongPressDragEnable = true // 是否运行长按触发拖动
    var initBackRes = 0             // 样式初始值
    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        var drag = 0
        var swipe = 0
        val layout = recyclerView?.layoutManager

        // 需要注意：GridLayoutManager是继承LinearManager，不能先判断linear再判断grid
        if(layout is GridLayoutManager || layout is StaggeredGridLayoutManager)
        {
            isNeedToSwipe = false
            // 网格和瀑布流不支持滑动
            drag = ItemTouchHelper.DOWN.or(ItemTouchHelper.UP)
                    .or(ItemTouchHelper.LEFT).or(ItemTouchHelper.RIGHT)
        } else if (layout is  LinearLayoutManager) // 线性布局
        {
            isNeedToSwipe = true
            if (layout.orientation == LinearLayoutManager.HORIZONTAL) {
                // 水平 左右拖动，上下滑动删除
                isLeftRightSwipe = false
                drag = ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)
                swipe = ItemTouchHelper.DOWN.or(ItemTouchHelper.UP)
            } else {
                // 垂直的 左右滑动删除。上下拖动
                isLeftRightSwipe = true
                drag = ItemTouchHelper.DOWN.or(ItemTouchHelper.UP)
                swipe = ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)
            }

        }
        return makeMovementFlags(drag, swipe)
    }

    // 1、拖动过程中，当curView与targetView达到一定的条件时，就会开始不断回调
    // 2、这个一定的条件，就是curView与最近的一个targetView，开始重合的时候（实际就是x,y偏移量）
    // 3、最近的一个targetView，往往就是curView的上一个或者下一个
    // 4、注意，当滑动过程中，由达到这个条件->未到达，这个函数也不再触发
    // 5、达到这个条件->未到达,很好理解，比如先向上拖动，达到条件，不断触发。此时又向下拖动，就会出现未达到条件的现象

    // 6、注意：上述说明是没有在该函数内部做数据交换的逻辑以及界面更新的情况下执行的流程
    // 7、如果在该函数中实现了数据交换以及界面更新，那么执行的流程就会发生改变
    // 8、触发一次，就表示curView的position已经更新到当前视图中的position了，并且实际的x,y偏移量已经置0
    // 9、下次想要再触发，必须再次与最近一个view重合才触发.（不再是第一次达到触发条件，就会不停调用，与1不一致）
    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        // 默认情况下，拖动的view在拖动结束后，会恢复到原本的位置，不会自动交换数据以及更新布局
        // 因此需要在这里对两个View的数据进行交换，并且更新两个view之间的布局
        val startPosition = viewHolder!!.adapterPosition
        val endPosition = target!!.adapterPosition

        // Log.d("----------",startPosition.toString().plus("---").plus(endPosition))
        // 交换_datas中的两个元素位置
        Collections.swap(_datas, startPosition, endPosition)

        // 更新布局
        recyclerView?.adapter?.notifyItemMoved(startPosition, endPosition)
        return true
    }

    //侧滑监听，当一个view侧滑删除结束后，才调用。
    //注意，是删除结束，如果滑动一半又恢复，是不会触发的
    //触发该函数，即一个View已经从视图中移除，但是界面没有更新，对应的数据也没有删除
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        // Toast.makeText(this@MainActivity, "onSwiped滑动结束", Toast.LENGTH_SHORT).show()
        // 删除当前位置的数据元素
        _datas.removeAt(viewHolder!!.adapterPosition)
        //  更新删除位置的视图
        _adapter.notifyItemRangeRemoved(viewHolder.adapterPosition, 1)
    }

    // 当前的itemView选择状态改变时（即一个view由unpress->press，或由press->unpress时触发）
    // 即，发生拖动或者侧滑时，会调用两次这个函数
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        // 根据状态做对应的处理，状态有三种，拖动，滑动，停止
        // 改变样式，只需要在非停止的时候改变
        // 那为什么不在停止的时候恢复样式
        // 当手指松开时，就会触发该函数，但是此时该View可能还受拖动，或者滑动事件的影响，而未处于暂停状态
        // 例如，滑动一半时，松开手指，此时回调该函数。但是view需要恢复原本位置，仍处于滑动状态，因此无法恢复样式
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder?.itemView?.setBackgroundColor(Color.GRAY)
        }
        //Toast.makeText(this@MainActivity, "onSelectedChanged", Toast.LENGTH_SHORT).show()
    }

    // 当一个View在交互结束后，并且他的相关动画已经执行完毕后，才调用
    // 该交互，指的是拖动或者滑动
    // 相关的动画执行完毕，可以认为是该View处于一个IDLE暂停的状态
    // 因此，这里才是最适合用于恢复view样式状态的函数。例如前面onSelectedChanged提到的问题
    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
        // Toast.makeText(this@MainActivity, "clearview", Toast.LENGTH_SHORT).show()
        viewHolder?.itemView?.setBackgroundResource(initBackRes)
        super.clearView(recyclerView, viewHolder)
    }


    //从itemView开始触发滑动或者拖动事件时，就会不断的回调。直到手指离开，并且状态变成静止才停止
    // 这里可以获取位置的偏移，进行相对应的逻辑处理，例如颜色状态，大小状态等。
    // 比较常见的就是滑动删除时，alpha不断变化的效果
    // 如果将这里看做动画插值器，那就很多事情可以做了(主要就是判断滑动距离与dx,dy的大小关系)
    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        // Log.d("--------onChildDraw", dX.toString().plus("---").plus(dY.toString()))
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        if (isNeedToSwipe) { // 允许滑动
            val alpha = 1 - if (isLeftRightSwipe) {
                Math.abs(dX.div(viewHolder!!.itemView.width)) // 允许左右滑动
            } else {
                Math.abs(dY.div(viewHolder!!.itemView.height)) // 允许上下滑动
            }
            viewHolder.itemView.alpha = alpha
        }

    }

    // 表示长按时，是否自动触发拖动事件，不影响侧滑事件
    // 默认返回true，即长按时触发拖动事件。如果recyclerView设置了长按事件，会出现冲突
    // 如果返回false，则无法触发拖动事件，可以通过startDrag开启拖动事件
    override fun isLongPressDragEnabled(): Boolean {
        return isLongPressDragEnable
    }
}