package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetUserFavoritesUseCase {
    operator fun invoke(page: Int)= repository.getFavoritesList(page)
}