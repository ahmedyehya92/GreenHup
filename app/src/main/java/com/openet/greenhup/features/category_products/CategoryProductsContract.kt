package com.openet.greenhup.features.category_products

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.entities.Plant
import com.openet.greenhup.core.LoadingHandler

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