package com.am_development.greenhup.features.categories_vendor.vendors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.am_development.entities.Category
import com.am_development.entities.Plant
import com.am_development.greenhup.R
import com.am_development.greenhup.features.categories_vendor.AdapterCategoriesList
import com.am_development.greenhup.features.categories_vendor.CategoriesImplPresenter
import com.am_development.greenhup.features.categories_vendor.CategoriesPresenter
import com.am_development.greenhup.features.category_products.CategoryProductsActivity
import com.am_development.greenhup.features.search.SearchActivity
import kotlinx.android.synthetic.main.activity_vendors.*

class VendorsActivity : AppCompatActivity(), VendorsView, AdapterCategoriesList.customButtonListener {

    var adapterVendors: AdapterCategoriesList?= null
    val vendorsList: MutableList<Category> = ArrayList()

    private val presenter: VendorsPresenter by lazy {
        VendorsImplPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendors)

        btn_search.setOnClickListener {
            startActivity(SearchActivity.instantiateIntent(this, et_search_query.text.toString()))
        }

        adapterVendors= AdapterCategoriesList(this, vendorsList)
        adapterVendors?.setCustomButtonListner(this)
        rv_vendors.adapter= adapterVendors

        presenter.getVendors()

        /*val plantsList: MutableList<Plant> = ArrayList()
        plantsList.add(Plant("1","INDOOR", "Groom", "500", "https://h7h9j4n8.stackpathcdn.com/wp-content/uploads/2014/07/Classiс-plant-transparent.png"))
        plantsList.add(Plant("1","INDOOR", "Groom", "500", "https://h7h9j4n8.stackpathcdn.com/wp-content/uploads/2014/07/Classiс-plant-transparent.png"))
        plantsList.add(Plant("1","INDOOR", "Groom", "500", "https://h7h9j4n8.stackpathcdn.com/wp-content/uploads/2014/07/Classiс-plant-transparent.png"))

        val catList: MutableList<Category> = ArrayList()
        catList.add(Category("1","Shop", plantsList))
        catList.add(Category("1","Plants", plantsList))
        catList.add(Category("1","Plants", plantsList))
        catList.add(Category("1","Plants", plantsList))
        catList.add(Category("1","Plants", plantsList))

        addVendors(catList)*/

    }

    override fun addVendors(categories: MutableList<Category>) {
        adapterVendors?.addAll(categories)
    }

    override fun showLoading() {

    }

    override fun finishLoading() {
    }

    override fun connectionError(message: String?) {
    }

    override fun faildLoading(message: Any) {
    }

    override fun onItemMoreClickListner(productModel: Category) {
    }

    override fun omItemPlantClickListener(plant: Plant) {
        startActivity(CategoryProductsActivity.instantiateIntent(this, plant.id))
    }
}
