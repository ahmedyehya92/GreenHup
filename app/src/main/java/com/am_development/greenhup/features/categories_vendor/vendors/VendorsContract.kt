package com.am_development.greenhup.features.categories_vendor.vendors

import com.am_development.entities.Category
import com.am_development.greenhup.core.LoadingHandler

interface VendorsView: LoadingHandler {
    fun addVendors(categories: MutableList<Category>)
}

interface VendorsPresenter {
    fun getVendors()
}