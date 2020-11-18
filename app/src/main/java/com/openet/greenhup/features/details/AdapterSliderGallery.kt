package com.openet.greenhup.features.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.openet.entities.SliderItem
import com.openet.greenhup.R
import com.bumptech.glide.Glide

class AdapterSliderGallery (
    private val plantsList: MutableList<SliderItem>,
    private val context: Context
): PagerAdapter()
{
    private var layoutInflater: LayoutInflater? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.item_home_header_view, container, false)
        val im_slider = view.findViewById<ImageView>(R.id.im_header)

        Glide.with(context)
            .load(plantsList[position].imageUrl)
            .into(im_slider)


        container.addView(view)

        return view
    }

    override fun getCount(): Int {
        return plantsList.size
    }


    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }

}