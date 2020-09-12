package com.am_development.greenhup.features.details

import androidx.lifecycle.DefaultLifecycleObserver
import com.am_development.entities.Plant
import com.am_development.greenhup.core.LoadingHandler

interface PlantDetailsView: LoadingHandler {
    fun addDetails(plant:Plant)
}

interface PlantDetailsPresenter: DefaultLifecycleObserver {
    fun getDetails(plantId: String)
}