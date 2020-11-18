package com.openet.greenhup.features.home

import com.openet.entities.Category
import com.openet.entities.MPackage
import com.openet.entities.Plant
import com.openet.greenhup.core.LoadingHandler

interface HomeView: LoadingHandler {
    fun addItemsToGallery(plantsList: MutableList<Plant>?)
    fun addItemsToHorizontalList(plantsList: MutableList<Plant>?)
    fun addItemsToPackagesSlider(packagesList: MutableList<MPackage>?)
    fun addCategories(categories: MutableList<Category>)
    fun addMainOffer(plant: Plant?)
}

interface HomePresenter {
    fun getHome()
}