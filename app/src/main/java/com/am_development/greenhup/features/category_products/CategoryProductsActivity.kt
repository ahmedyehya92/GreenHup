package com.am_development.greenhup.features.category_products

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.am_development.entities.KEY_CATEGORY_ID
import com.am_development.entities.KEY_SEARCH_QUERY
import com.am_development.entities.Plant
import com.am_development.greenhup.R
import com.am_development.greenhup.core.PaginationAdapterCallBack
import com.am_development.greenhup.core.PaginationStaggardScrollListener
import com.am_development.greenhup.core.RequestIntervalHandler2
import com.am_development.greenhup.features.search.SearchActivity
import kotlinx.android.synthetic.main.activity_category_products.*
import java.util.ArrayList

class CategoryProductsActivity : AppCompatActivity(), CategoryProductsView, AdapterProducts.customButtonListener, PaginationAdapterCallBack {
    private val PAGE_START = 1
    var categoryId: String?= null
    private var isLoadingV: Boolean = false
    private var isLastPageV: Boolean = false
    private var TOTAL_PAGES: Int = 50
    private lateinit var requestIntervalHandler: RequestIntervalHandler2
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    val totalPageCount = 20
    private var requestIsFinished= true
    private var currentPage = PAGE_START
    private var arrayList: MutableList<Plant> = ArrayList()
    var adapter: AdapterProducts = AdapterProducts(this, arrayList)
    private val presenter: CategoryProductsPresenter by lazy {
        CategoryProductsImplPresenter(this)
    }
    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> categoryId?.let { it1 -> presenter.getPlantsList(it1, currentPage) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_products)
        setInitiateValues()
        setupRequestHandlerView()
        setupProductsListRecyclerView()
        implementScrolListener()
        currentPage = PAGE_START
        categoryId?.let { presenter.getPlantsList(it, currentPage) }
    }

    private fun setupProductsListRecyclerView() {
        adapter.setCustomButtonListner(this)
        adapter.setPagingAdapterCallback(this)
        rv_category_products.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun implementScrolListener() {
        rv_category_products.addOnScrollListener(object :
            PaginationStaggardScrollListener(rv_category_products.layoutManager as androidx.recyclerview.widget.StaggeredGridLayoutManager) {
            //protected fun hideCatList() {}


            override fun loadMoreItems() {
                Log.e("CategoryProducts", "isLastPage= ${isLastPageV}")
                if(!isLastPageV && requestIsFinished) {
                    isLoadingV = true
                    currentPage += 1
                    categoryId?.let { presenter.getPlantsList(it, currentPage) }
                }
            }

            override val totalPageCount: Int = TOTAL_PAGES

            override val isLastPage: Boolean = isLastPageV

            override val isLoading: Boolean = isLoadingV
        })
    }

    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, this, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)

    }


    private fun setInitiateValues() {
        categoryId= intent.getStringExtra(KEY_CATEGORY_ID)
    }
    companion object {
        fun instantiateIntent(context: Context, categoryId: String): Intent {
            return Intent(context, CategoryProductsActivity::class.java).apply { this.putExtra(
                KEY_CATEGORY_ID, categoryId) }
        }
    }

    override fun addPlants(plantsList: MutableList<Plant>) {
        adapter.addAll(plantsList)
    }

    override fun setLastPageTrue() {
        isLastPageV = true
    }

    override fun addLoadingFooter() {
        adapter.addLoadingFooter()
    }

    override fun removeLoadingFooter() {
        adapter?.removeLoadingFooter()
    }

    override fun showRetryAdapter() {
        adapter?.showRetry(true,"Error")
    }

    override fun setIsLoadingFalse() {
        isLoadingV = false
    }

    override fun showEmptyViewForList() {
        finishLoading()
        //view_empty_list.visibility = View.VISIBLE
    }

    override fun setRequestIsFinished(requestIsFinished: Boolean) {
        this.requestIsFinished= requestIsFinished
    }

    override fun showLoading() {
        requestIntervalHandler.showLoadingView(null)
    }

    override fun finishLoading() {
        requestIntervalHandler.finishLoading()
    }

    override fun connectionError(message: String?) {
        requestIntervalHandler.showErrorView("error connection, try again!")
    }

    override fun faildLoading(message: Any) {
    }

    override fun onItemPlantsClickListener(plant: Plant) {
    }

    override fun retryPageLoad() {
        categoryId?.let { presenter.getPlantsList(it, currentPage) }
    }

}