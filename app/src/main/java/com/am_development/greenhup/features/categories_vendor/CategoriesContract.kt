package com.am_development.greenhup.features.categories_vendor

import com.am_development.entities.Category

interface CategoriesView{
    fun addCategories(categories: MutableList<Category>)
}