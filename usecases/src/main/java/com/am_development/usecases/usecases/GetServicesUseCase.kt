package com.am_development.usecases.usecases

import com.am_development.usecases.repository.repository

class GetServicesUseCase {
    operator fun invoke() = repository.services()
}