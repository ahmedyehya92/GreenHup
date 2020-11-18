package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetPackageDetailsUseCase {
    operator fun invoke(packageId: String)= repository.packageDetails(packageId)
}