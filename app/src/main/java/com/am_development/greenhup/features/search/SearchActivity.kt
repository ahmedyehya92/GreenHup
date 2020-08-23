package com.am_development.greenhup.features.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.am_development.entities.KEY_SEARCH_QUERY
import com.am_development.entities.Plant
import com.am_development.greenhup.R
import com.am_development.greenhup.features.categories_vendor.AdapterPlants
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchView, AdapterPlants.customButtonListener {

    var searchQueryText: String?= null

    var adapterPlantsList: AdapterPlants?= null
    val plantsList: MutableList<Plant> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setInitiateValues()
        searchQueryText?.apply { tv_search_query.setText(searchQueryText)}

        adapterPlantsList = AdapterPlants(this, plantsList)
        adapterPlantsList?.setCustomButtonListner(this)
        rv_search_results.adapter= adapterPlantsList

        val planList: MutableList<Plant> = ArrayList()

        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))

        addToSearchList(planList)

    }

    private fun setInitiateValues() {
        searchQueryText= intent.getStringExtra(KEY_SEARCH_QUERY)
    }

    companion object {
        fun instantiateIntent(context: Context, queryText: String): Intent {
            return Intent(context, SearchActivity::class.java).apply { this.putExtra(
                KEY_SEARCH_QUERY, queryText) }
        }
    }

    override fun addToSearchList(searchItemsList: MutableList<Plant>) {
        adapterPlantsList?.addAll(searchItemsList)
    }

    override fun onItemPlantsClickListener(plant: Plant) {

    }

}
