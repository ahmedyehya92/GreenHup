package com.openet.usecases.usecases

import com.openet.entities.ResponsePlantDetails
import com.openet.usecases.repository.repository
import io.reactivex.Single

class GetPlantDetailsUseCase {
    operator fun invoke(plantId: String, authorized: Boolean): Single<ResponsePlantDetails>
    {
        return if (authorized)
            repository.plantDetailsAuthorized(plantId)
        else
            repository.plantDetails(plantId)
    }
}