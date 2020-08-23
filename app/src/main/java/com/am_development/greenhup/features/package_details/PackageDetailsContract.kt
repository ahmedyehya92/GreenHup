package com.am_development.greenhup.features.package_details

import com.am_development.entities.Plant

interface PackageDetailsView{
    fun addToPlants(plantsList: MutableList<Plant>)
}