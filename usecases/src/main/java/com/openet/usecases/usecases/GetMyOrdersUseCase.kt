package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetMyOrdersUseCase {
    operator fun invoke()= repository.getMyOrders()
}