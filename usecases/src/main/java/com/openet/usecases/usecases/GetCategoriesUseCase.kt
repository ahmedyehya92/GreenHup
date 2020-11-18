package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetCategoriesUseCase {
    operator fun invoke()= repository.categories()
}