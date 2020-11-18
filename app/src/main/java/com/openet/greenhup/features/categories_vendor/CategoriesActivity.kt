package com.openet.greenhup.features.categories_vendor

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import com.openet.entities.Category
import com.openet.entities.Plant
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.core.RequestIntervalHandler2
import com.openet.greenhup.features.category_products.CategoryProductsActivity
import com.openet.greenhup.features.details.PlantDetailsActivity
import com.openet.greenhup.features.search.SearchActivity
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.activity_categories.btn_search
import kotlinx.android.synthetic.main.activity_categories.et_search_query
import kotlinx.android.synthetic.main.activity_categories.lout_loading_interval_view_container

class CategoriesActivity : BaseActivity(), CategoriesView, AdapterCategoriesList.customButtonListener {

    var adapterCategories: AdapterCategoriesList?= null
    val categoriesList: MutableList<Category> = ArrayList()

    private lateinit var requestIntervalHandler: RequestIntervalHandler2

    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> presenter.getCategories()
        }
    }

    private val presenter: CategoriesPresenter by lazy {
        CategoriesImplPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        et_search_query.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                startActivity(
                    SearchActivity.instantiateIntent(
                        this,
                        et_search_query.text.toString()
                    )
                )
                return@OnEditorActionListener true
            }
            false
        })



        btn_search.setOnClickListener {
            startActivity(SearchActivity.instantiateIntent(this, et_search_query.text.toString()))
        }

        adapterCategories= AdapterCategoriesList(this, categoriesList)
        adapterCategories?.setCustomButtonListner(this)
        rv_categories.adapter= adapterCategories

        setupRequestHandlerView()

        presenter.getCategories()

     /*   val plantsList: MutableList<Plant> = ArrayList()
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

        addCategories(catList)*/
    }

    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, this, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)
    }

    override fun addCategories(categories: MutableList<Category>) {
        adapterCategories?.addAll(categories)
    }

    override fun showLoading() {
        requestIntervalHandler.showLoadingView(null)
    }

    override fun finishLoading() {
        requestIntervalHandler.finishLoading()
    }

    override fun connectionError(message: String?) {
        requestIntervalHandler.showErrorView(getString(R.string.error_connection))
    }
    override fun faildLoading(message: Any) {
    }

    override fun onItemMoreClickListner(productModel: Category) {
        startActivity(CategoryProductsActivity.instantiateIntent(this, productModel.id))
    }

    override fun omItemPlantClickListener(plant: Plant) {
        startActivity(PlantDetailsActivity.instantiateIntent(this, plant))
    }
}
