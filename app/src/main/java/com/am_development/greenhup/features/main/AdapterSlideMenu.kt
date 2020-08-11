package com.am_development.greenhup.features.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.am_development.entities.ItemNovigtionMenu
import com.am_development.greenhup.R
import com.am_development.greenhup.customviews.CustomeFontTextView
import java.util.ArrayList

class AdapterSlideMenu (internal var context: Context, items: ArrayList<ItemNovigtionMenu>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var customeListener: CustomeListener
    lateinit var viewHolder: ViewHolder
    internal var selectedPosition: Int = 0
    var items = items

    override fun getItemCount() = items.size


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val mInflater = LayoutInflater.from(viewGroup.context)


        return ViewHolder(
            mInflater.inflate(R.layout.view_slide_menu_item, viewGroup, false)
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: ItemNovigtionMenu = items[position]
        val itemViewHolder = holder as ViewHolder


        //item.iconResourceId?.apply { itemViewHolder.imIcon!!.setImageResource(this) }
        itemViewHolder.tvTitle!!.text = item.title
        itemViewHolder.loutContainer!!.setOnClickListener {
            //if (selectedPosition != position || position ==0)
            //{

            getItem(selectedPosition)!!.isSelected = false
            selectedPosition = position
            item.isSelected = true
            customeListener.onSelectedSlideMenuItem(position)
            //viewHolder.lout_container.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_blue_frame));

            //}
        }
    }





    override fun getItemViewType(position: Int): Int {
        return position
    }


    fun remove(r: ItemNovigtionMenu) {
        val position = items.indexOf(r)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    fun getItem(position: Int): ItemNovigtionMenu {
        return items[position]
    }

    fun isEmpty(): Boolean {
        return itemCount == 0
    }

    fun add(r: ItemNovigtionMenu) {
        items.add(r)
        notifyItemInserted(items.size - 1)

    }

    fun addAll(opResults: MutableList<ItemNovigtionMenu>) {
        for (result in opResults) {
            add(result)
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val loutContainer by lazy { itemView.findViewById<LinearLayout>(R.id.lout_slide_menu_item_container) }
        val tvTitle by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_title) }
        //val imIcon by lazy { itemView.findViewById<ImageView>(R.id.im_icon) }
    }

    interface CustomeListener {
        fun onSelectedSlideMenuItem(position: Int)
    }

    fun setCustomButtonListner(listener: CustomeListener) {
        this.customeListener = listener
    }


}