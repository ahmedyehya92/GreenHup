package com.openet.greenhup.features.details

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.entities.Plant
import com.openet.greenhup.core.LoadingHandler

interface PlantDetailsView: LoadingHandler {
    fun addDetails(plant:Plant)
    fun changeItemAmount(newQuantity: Int, plantId: Int)
    fun changeFavoriteState(favoriteState: Boolean)
    fun removeItemFromCart(id: String)
}

interface PlantDetailsPresenter: DefaultLifecycleObserver {
    fun getDetails(plantId: String)
    fun changeCartItemAmount(id: String, newQuantity: Int)
    fun changeFavoriteStateProduct(productId: String, favorite: Boolean)
    fun removeItemFromCart(id: String)
}