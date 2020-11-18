package com.openet.greenhup.features.cart

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.entities.CartItem
import com.openet.greenhup.core.LoadingHandler

interface CartView: LoadingHandler{
    fun addToCartList(items: MutableList<CartItem>)
    fun changeItemAmount(position: Int, newQuantity: Int)
}

interface CartPresenter: DefaultLifecycleObserver {
    fun getCart()
    fun changeCartItemAmount(id: String, position: Int, newQuantity: Int, type: String= "product")
}