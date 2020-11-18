package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetCategoryProductsUseCase {
    operator fun invoke(categoryId: String, page: Int)= repository.categoryProducts(categoryId, page)
}