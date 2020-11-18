package com.openet.usecases.usecases

import com.openet.entities.ResponseAddToCart
import com.openet.usecases.repository.repository
import io.reactivex.Single

class StatePackageToCartUseCase {

    fun invoke(packageId: String, quantity: String, added: Boolean): Single<ResponseAddToCart>
    {
        return if(added)
            repository.removePackageFromCart(packageId)
        else
            repository.addPackageToCart(packageId, quantity)
    }
}