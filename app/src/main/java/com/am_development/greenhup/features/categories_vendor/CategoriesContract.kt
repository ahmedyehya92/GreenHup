package com.am_development.greenhup.features.categories_vendor

import com.am_development.entities.Category
import com.am_development.greenhup.core.LoadingHandler

interface CategoriesView: LoadingHandler{
    fun addCategories(categories: MutableList<Category>)
}

interface CategoriesPresenter{
    fun getCategories()
}