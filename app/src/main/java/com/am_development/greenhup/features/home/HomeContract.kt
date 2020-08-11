package com.am_development.greenhup.features.home

import com.am_development.entities.Plant

interface HomeView {
    fun addItemsToGallery(plantsList: MutableList<Plant>)
    fun addItemsToHorizontalList(plantsList: MutableList<Plant>)
}

interface HomePresenter {

}