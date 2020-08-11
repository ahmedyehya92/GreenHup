package com.am_development.greenhup.features.categories_vendor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.am_development.entities.Category
import com.am_development.entities.Plant
import com.am_development.greenhup.R
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity(), CategoriesView, AdapterCategoriesList.customButtonListener {

    var adapterCategories: AdapterCategoriesList?= null
    val categoriesList: MutableList<Category> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        adapterCategories= AdapterCategoriesList(this, categoriesList)
        adapterCategories?.setCustomButtonListner(this)
        rv_categories.adapter= adapterCategories

        val plantsList: MutableList<Plant> = ArrayList()
        plantsList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        plantsList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        plantsList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        plantsList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))

        val catList: MutableList<Category> = ArrayList()
        catList.add(Category("1","Plants", plantsList))
        catList.add(Category("1","Plants", plantsList))
        catList.add(Category("1","Plants", plantsList))
        catList.add(Category("1","Plants", plantsList))
        catList.add(Category("1","Plants", plantsList))

        addCategories(catList)
    }

    override fun addCategories(categories: MutableList<Category>) {
        adapterCategories?.addAll(categories)
    }

    override fun onItemMoreClickListner(productModel: Category) {
    }

    override fun omItemPlantClickListener(plant: Plant) {
    }
}