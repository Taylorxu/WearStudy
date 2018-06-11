package com.xuzhiguang.wearstudy

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import android.support.wearable.activity.WearableActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.opengl.ETC1.getHeight
import android.support.annotation.IntegerRes
import com.xuzhiguang.wearstudy.R.id.recycler_launcher_view


class MainActivity : WearableActivity() {
    var dataArray = mutableListOf<Item>()
    var mipmap = mutableListOf(R.mipmap.ice_tuo_luo, R.mipmap.kit, R.mipmap.qi_qiu, R.mipmap.raceing, R.mipmap.shou_bing, R.mipmap.water_gun, R.mipmap.xiang_qi, R.mipmap.yao_gan)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Enables Always-on
        setAmbientEnabled()
        createData()

        //这将使列表上的第一个项目和最后一个项目在屏幕上垂直居中对齐。
        recycler_launcher_view.isEdgeItemsCenteringEnabled = true
        //循环滚动
        recycler_launcher_view.isCircularScrollingGestureEnabled = true
        //将识别手势的屏幕边缘附近虚拟“屏幕边框”的宽度 — setBezelFraction —默认值设为 1。该值可以视图半径的一小部分表示。
        recycler_launcher_view.bezelFraction = 0.5f
        //用户必须旋转多少度才能滚动一个屏幕高度。这将有效地影响滚动速度 — setScrollDegreesPerScreen — 默认值设为 180 度
        recycler_launcher_view.scrollDegreesPerScreen = 90.toFloat()

        recycler_launcher_view.layoutManager = WearableLinearLayoutManager(this, CustomScrollingLayoutCallback())
        recycler_launcher_view.adapter = MyAdapter(dataArray)
    }

    fun createData() {
        repeat(mipmap.size) {
            var item = Item(mipmap[it], "item$it")
            dataArray.add(item)
        }
    }

    class MyAdapter(var list: MutableList<Item>) : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, null, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.fill(list[position])
        }

    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private var img_view: ImageView? = null
        private var text_view: TextView? = null

        init {
            img_view = itemView?.findViewById(R.id.img_view)
            text_view = itemView?.findViewById(R.id.tv_name)
        }

        fun fill(item: Item) {
            img_view?.setBackgroundResource(item.i)
            text_view?.text = item.s
        }

    }

    class Item(var i: Int, var s: String)

    inner class CustomScrollingLayoutCallback : WearableLinearLayoutManager.LayoutCallback() {

        private var mProgressToCenter: Float = 0.toFloat()
        private val MAX_ICON_PROGRESS = 0.65f
        override fun onLayoutFinished(child: View, parent: RecyclerView) {

            // Figure out % progress from top to bottom
            val centerOffset = child.height.toFloat() / 2.0f / recycler_launcher_view.height
            val yRelativeToCenterOffset = child.y / recycler_launcher_view.height + centerOffset

            // Normalize for center
            mProgressToCenter = Math.abs(0.5f - yRelativeToCenterOffset)
            // Adjust to the maximum scale
            mProgressToCenter = Math.min(mProgressToCenter, MAX_ICON_PROGRESS)

            child.scaleX = 1 - mProgressToCenter
            child.scaleY = 1 - mProgressToCenter
        }

    }
}
