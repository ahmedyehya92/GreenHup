package com.openet.greenhup.features.search

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.entities.Plant
import com.openet.greenhup.core.LoadingHandler

interface SearchView: LoadingHandler {
    fun addToSearchList(searchItemsList: MutableList<Plant>)
    fun setLastPageTrue()
    fun addLoadingFooter()
    fun removeLoadingFooter()
    fun showRetryAdapter()
    fun setIsLoadingFalse()
    fun showEmptyViewForList()
    fun setRequestIsFinished(requestIsFinished: Boolean)
}

interface SearchPresenter: DefaultLifecycleObserver {
    fun getSearchResult(query: String, page: Int)
}