package com.am_development.greenhup.features.home

import com.am_development.entities.MPackage
import com.am_development.entities.Plant
import com.am_development.entities.SliderItem
import com.am_development.greenhup.core.LoadingHandler

interface HomeView: LoadingHandler {
    fun addItemsToGallery(plantsList: MutableList<SliderItem>)
    fun addItemsToHorizontalList(plantsList: MutableList<Plant>)
    fun addItemsToPackagesSlider(packagesList: MutableList<MPackage>)
    fun addMainOffer(plant: Plant)
}

interface HomePresenter {
    fun getHome()
}