package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetUserInfoUseCase {
    operator fun invoke() = repository.getUserInfo()
}