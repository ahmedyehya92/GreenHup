package com.am_development.usecases.usecases

import com.am_development.usecases.repository.repository

class GetHomeUseCase {
    operator fun invoke() = repository.home()
}