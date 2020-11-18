package com.openet.greenhup.features.favorites

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.entities.Plant
import com.openet.greenhup.core.LoadingHandler


interface FavoritesView: LoadingHandler{
    fun addPlants(plantsList: MutableList<Plant>)
    fun setLastPageTrue()
    fun addLoadingFooter()
    fun removeLoadingFooter()
    fun showRetryAdapter()
    fun setIsLoadingFalse()
    fun showEmptyViewForList()
    fun setRequestIsFinished(requestIsFinished: Boolean)
}

interface FavoritesPresenter: DefaultLifecycleObserver{
    fun getFavorites(page: Int)
}
