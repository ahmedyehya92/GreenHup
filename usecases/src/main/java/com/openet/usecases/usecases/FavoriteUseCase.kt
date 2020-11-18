package com.openet.usecases.usecases

import com.openet.entities.ResponseFavoriteProduct
import com.openet.usecases.repository.repository
import io.reactivex.Single

class FavoriteUseCase {
    fun invoke(productId: String, favorited: Boolean): Single<ResponseFavoriteProduct>
    {
        if(favorited)
            return repository.removeFromFavorites(productId)

        else
            return repository.addToFavorites(productId)
    }
}