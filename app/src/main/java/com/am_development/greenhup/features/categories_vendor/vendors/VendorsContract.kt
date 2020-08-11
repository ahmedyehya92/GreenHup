package com.am_development.greenhup.features.categories_vendor.vendors

import com.am_development.entities.Category

interface VendorsView {
    fun addVendors(categories: MutableList<Category>)
}