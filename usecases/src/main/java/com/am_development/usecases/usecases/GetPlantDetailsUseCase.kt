package com.am_development.usecases.usecases

import com.am_development.usecases.repository.repository

class GetPlantDetailsUseCase {
    operator fun invoke(plantId: String)= repository.plantDetails(plantId)
}