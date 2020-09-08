package com.am_development.greenhup.features.category_products

import androidx.lifecycle.DefaultLifecycleObserver
import com.am_development.entities.Plant
import com.am_development.greenhup.core.LoadingHandler

interface CategoryProductsView: LoadingHandler {
    fun addPlants(plantsList: MutableList<Plant>)
    fun setLastPageTrue()
    fun addLoadingFooter()
    fun removeLoadingFooter()
    fun showRetryAdapter()
    fun setIsLoadingFalse()
    fun showEmptyViewForList()
    fun setRequestIsFinished(requestIsFinished: Boolean)
}

interface CategoryProductsPresenter: DefaultLifecycleObserver {
    fun getPlantsList(categoryId: String, page: Int)
}