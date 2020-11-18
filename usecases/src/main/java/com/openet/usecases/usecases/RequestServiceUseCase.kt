package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class RequestServiceUseCase {
    operator fun invoke(serviceId: String, note: String)= repository.requestService(serviceId, note)
}