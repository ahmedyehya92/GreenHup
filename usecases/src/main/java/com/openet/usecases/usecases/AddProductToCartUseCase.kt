package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class AddProductToCartUseCase {
    operator fun invoke(productId: String, quantity: String)= repository.addItemToCart(productId, quantity)
}