package com.am_development.usecases.usecases

import com.am_development.usecases.repository.repository

class GetCategoriesUseCase {
    operator fun invoke()= repository.categories()
}