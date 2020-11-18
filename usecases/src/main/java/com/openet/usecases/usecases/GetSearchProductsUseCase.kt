package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetSearchProductsUseCase {
    operator fun invoke(query: String, page: Int) = repository.searchProducts(query, page)
}