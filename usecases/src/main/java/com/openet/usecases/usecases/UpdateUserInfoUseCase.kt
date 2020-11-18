package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class UpdateUserInfoUseCase {
    operator fun invoke(name: String,
                        phone: String,
                        country: String,
                        city: String,
                        address: String,
                        ziip: String) = repository.updateUserInfoUseCase(name, phone, country, city, address, ziip)
}