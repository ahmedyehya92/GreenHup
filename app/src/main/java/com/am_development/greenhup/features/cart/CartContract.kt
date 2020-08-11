package com.am_development.greenhup.features.cart

import com.am_development.entities.CartItem

interface CartView{
    fun addToCartList(items: MutableList<CartItem>)
}