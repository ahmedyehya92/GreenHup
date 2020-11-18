package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetCountriesUseCase{
    operator fun invoke()= repository.getCountries()
}