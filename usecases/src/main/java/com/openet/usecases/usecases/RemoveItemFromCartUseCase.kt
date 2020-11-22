package com.openet.usecases.usecases

import com.openet.entities.ResponseAddToCart
import com.openet.usecases.repository.repository
import io.reactivex.Single

class RemoveItemFromCartUseCase {
    fun invoke(id: String, isProduct: Boolean): Single<ResponseAddToCart>
    {
        return if(isProduct)
            repository.removeProductFromCart(id)
        else
            repository.removePackageFromCart(id)
    }
}