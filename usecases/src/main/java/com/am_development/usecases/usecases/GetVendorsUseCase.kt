package com.am_development.usecases.usecases

import com.am_development.usecases.repository.repository

class GetVendorsUseCase {
    operator fun invoke()= repository.vendors()
}