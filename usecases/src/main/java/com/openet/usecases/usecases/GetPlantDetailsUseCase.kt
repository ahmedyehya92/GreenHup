package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetPlantDetailsUseCase {
    operator fun invoke(plantId: String)= repository.plantDetails(plantId)
}