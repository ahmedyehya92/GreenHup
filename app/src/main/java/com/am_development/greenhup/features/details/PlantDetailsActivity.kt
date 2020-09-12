package com.am_development.greenhup.features.details

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.am_development.entities.Plant
import com.am_development.greenhup.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*
import kotlin.math.roundToInt


class PlantDetailsActivity : AppCompatActivity(), PlantDetailsView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

    }

    override fun addDetails(plant: Plant) {
        tv_category_name.text= plant.categoryName
        //rating_bar.rating= plant.
        Glide.with(this)
            .load(plant.imageUrl)
            .into(im_plant)
        tv_price.text= "Price: \$${plant.price}"
        //tv_vendor_name.text= plant.
        if(plant.specifications?.size != 0)
        {
            lout_specifications.visibility= View.VISIBLE
            val specifications=""
            plant.specifications?.forEach {
                specifications.plus(it.specName)
                specifications.plus(":")
                specifications.plus(it.value)
                specifications.plus("\n")
            }
            tv_specifications.text= specifications
        }
    }

    override fun showLoading() {
    }

    override fun finishLoading() {
    }

    override fun connectionError(message: String?) {
    }

    override fun faildLoading(message: Any) {
    }
}
