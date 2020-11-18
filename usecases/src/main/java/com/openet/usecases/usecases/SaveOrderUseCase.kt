package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class SaveOrderUseCase {
    operator fun invoke(comments: String, name: String, phone: String, email: String, address: String) = repository.saveOrder(comments, name, phone, email, address)
}