package com.openet.greenhup.features.categories_vendor

import com.openet.entities.Category
import com.openet.greenhup.core.LoadingHandler

interface CategoriesView: LoadingHandler{
    fun addCategories(categories: MutableList<Category>)
}

interface CategoriesPresenter{
    fun getCategories()
}