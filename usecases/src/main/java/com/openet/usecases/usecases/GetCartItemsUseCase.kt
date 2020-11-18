package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetCartItemsUseCase {
    operator fun invoke()= repository.getCartItems()
}