package com.am_development.greenhup.features.package_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.am_development.entities.Plant
import com.am_development.greenhup.R
import kotlinx.android.synthetic.main.activity_package_details.*

class PackageDetailsActivity : AppCompatActivity(), PackageDetailsView {

    private val plantsList: MutableList<Plant> = ArrayList()
    private var adapterPackageProductsList: AdapterPackageProductsList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_details)

        adapterPackageProductsList= AdapterPackageProductsList(this, plantsList)
        rv_plants.adapter= adapterPackageProductsList
        val planList: MutableList<Plant> = ArrayList()
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        addToPlants(planList)
    }

    override fun addToPlants(plantsList: MutableList<Plant>) {
        adapterPackageProductsList?.addAll(plantsList)
    }
}
