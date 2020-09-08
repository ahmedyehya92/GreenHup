package com.am_development.usecases.usecases

import com.am_development.usecases.repository.repository

class GetCategoryProductsUseCase {
    operator fun invoke(categoryId: String, page: Int)= repository.categoryProducts(categoryId, page)
}