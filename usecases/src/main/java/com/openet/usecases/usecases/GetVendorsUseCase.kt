package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetVendorsUseCase {
    operator fun invoke()= repository.vendors()
}