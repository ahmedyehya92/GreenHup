package com.openet.greenhup.features.categories_vendor.vendors

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.Observer
import com.openet.entities.Category
import com.openet.entities.Plant
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.core.RequestIntervalHandler2
import com.openet.greenhup.features.categories_vendor.AdapterCategoriesList
import com.openet.greenhup.features.category_products.CategoryProductsActivity
import com.openet.greenhup.features.search.SearchActivity
import kotlinx.android.synthetic.main.activity_vendors.*


class VendorsActivity : BaseActivity(), VendorsView, AdapterCategoriesList.customButtonListener {

    var adapterVendors: AdapterCategoriesList?= null
    val vendorsList: MutableList<Category> = ArrayList()

    private lateinit var requestIntervalHandler: RequestIntervalHandler2

    private val presenter: VendorsPresenter by lazy {
        VendorsImplPresenter(this)
    }

    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> presenter.getVendors()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendors)

        et_search_query.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                startActivity(SearchActivity.instantiateIntent(this, et_search_query.text.toString()))
                return@OnEditorActionListener true
            }
            false
        })

        btn_search.setOnClickListener {
            startActivity(SearchActivity.instantiateIntent(this, et_search_query.text.toString()))
        }



        adapterVendors= AdapterCategoriesList(this, vendorsList)
        adapterVendors?.setCustomButtonListner(this)
        rv_vendors.adapter= adapterVendors

        setupRequestHandlerView()

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


    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, this, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)
    }

    override fun addVendors(categories: MutableList<Category>) {
        adapterVendors?.addAll(categories)
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
    }

    override fun omItemPlantClickListener(plant: Plant) {
        startActivity(CategoryProductsActivity.instantiateIntent(this, plant.id))
    }
}
