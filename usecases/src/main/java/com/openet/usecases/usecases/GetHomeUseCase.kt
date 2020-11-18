package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetHomeUseCase {
    operator fun invoke() = repository.home()
}