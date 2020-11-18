package com.openet.greenhup.features.categories_vendor.vendors

import com.openet.entities.Category
import com.openet.greenhup.core.LoadingHandler

interface VendorsView: LoadingHandler {
    fun addVendors(categories: MutableList<Category>)
}

interface VendorsPresenter {
    fun getVendors()
}