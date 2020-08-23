package com.am_development.greenhup.features.search

import com.am_development.entities.Plant

interface SearchView{
    fun addToSearchList(searchItemsList: MutableList<Plant>)
}