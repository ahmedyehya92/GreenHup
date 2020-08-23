package com.am_development.greenhup.features.favorites

import com.am_development.entities.Plant

class FavoritesContract {
    interface FavoritesView{
        fun addPlants(plantsList: MutableList<Plant>)
    }
}