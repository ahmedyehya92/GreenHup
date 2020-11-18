package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class RegisterUseCase {
    operator fun invoke(name: String,
                        email: String,
                        password: String,
                        passwordConfirmation: String,
                        mobileNumber: String) = repository.register(name, email, password, passwordConfirmation, mobileNumber)
}