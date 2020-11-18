package com.openet.greenhup.features.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import com.openet.entities.KEY_SEARCH_QUERY
import com.openet.entities.Plant
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.core.PaginationAdapterCallBack
import com.openet.greenhup.core.PaginationStaggardScrollListener
import com.openet.greenhup.core.RequestIntervalHandler2
import com.openet.greenhup.features.categories_vendor.AdapterPlants
import com.openet.greenhup.features.details.PlantDetailsActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.btn_search
import kotlinx.android.synthetic.main.activity_search.lout_loading_interval_view_container

class SearchActivity : BaseActivity(), SearchView, AdapterPlants.customButtonListener,
    PaginationAdapterCallBack {
    private val PAGE_START = 1
    var searchQueryText: String?= null
    private var isLoadingV: Boolean = false
    private var isLastPageV: Boolean = false
    private var TOTAL_PAGES: Int = 50
    private lateinit var requestIntervalHandler: RequestIntervalHandler2
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    val totalPageCount = 20
    private var requestIsFinished= true
    private var currentPage = PAGE_START
    var adapterPlantsList: AdapterPlants?= null
    val plantsList: MutableList<Plant> = ArrayList()

    private val presenter: SearchPresenter by lazy {
        SearchImplPresenter(this)
    }

    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> searchQueryText?.let { it1 -> presenter.getSearchResult(it1, currentPage) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setInitiateValues()
        searchQueryText?.apply { tv_search_query.setText(searchQueryText)}





        setupRequestHandlerView()
        setupProductsListRecyclerView()
        implementScrolListener()
        currentPage = PAGE_START
        searchQueryText?.let { presenter.getSearchResult(it, currentPage) }

        tv_search_query.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                tv_search_query.text?.let {
                    if(it.isNotEmpty() && requestIsFinished)
                    {
                        currentPage=1
                        adapterPlantsList?.clear()
                        presenter.getSearchResult(it.toString(), currentPage)
                    }
                }
                return@OnEditorActionListener true
            }
            false
        })

        btn_search.setOnClickListener {
            tv_search_query.text?.let {
                if(it.isNotEmpty() && requestIsFinished)
                {
                    currentPage=1
                    adapterPlantsList?.clear()
                    presenter.getSearchResult(it.toString(), currentPage)
                }
            }

        }


      /*  val planList: MutableList<Plant> = ArrayList()

        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))

        addToSearchList(planList)*/

    }


    private fun implementScrolListener() {
        rv_search_results.addOnScrollListener(object :
            PaginationStaggardScrollListener(rv_search_results.layoutManager as androidx.recyclerview.widget.StaggeredGridLayoutManager) {
            //protected fun hideCatList() {}


            override fun loadMoreItems() {
                Log.e("CategoryProducts", "isLastPage= ${isLastPageV}")
                if(!isLastPageV && requestIsFinished) {
                    isLoadingV = true
                    currentPage += 1
                    searchQueryText?.let { presenter.getSearchResult(it, currentPage) }
                }
            }

            override val totalPageCount: Int = TOTAL_PAGES

            override val isLastPage: Boolean = isLastPageV

            override val isLoading: Boolean = isLoadingV
        })
    }

    private fun setupProductsListRecyclerView() {
        adapterPlantsList = AdapterPlants(this, plantsList)
        adapterPlantsList?.setCustomButtonListner(this)
        rv_search_results.adapter= adapterPlantsList
    }

    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, this, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)
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

    override fun setLastPageTrue() {
        isLastPageV = true
    }

    override fun addLoadingFooter() {
        adapterPlantsList?.addLoadingFooter()

    }

    override fun removeLoadingFooter() {
        adapterPlantsList?.removeLoadingFooter()
    }

    override fun showRetryAdapter() {
        adapterPlantsList?.showRetry(true,"Error")
    }

    override fun setIsLoadingFalse() {
        isLoadingV = false
    }

    override fun showEmptyViewForList() {
        finishLoading()
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
        requestIntervalHandler.showErrorView(getString(R.string.error_connection))
    }

    override fun faildLoading(message: Any) {
    }

    override fun onItemPlantsClickListener(plant: Plant) {
        startActivity(PlantDetailsActivity.instantiateIntent(this, plant))

    }

    override fun retryPageLoad() {

    }

}
