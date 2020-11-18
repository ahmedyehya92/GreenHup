package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetServicesUseCase {
    operator fun invoke() = repository.services()
}