package com.openet.greenhup.features.register

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.greenhup.core.LoadingHandler

interface RegisterView: LoadingHandler {
    fun successfulRegister()
    fun failedRegister()
}

interface RegisterPresenter: DefaultLifecycleObserver {
    fun register(name: String, email: String, password: String, passwordConfirmation: String, mobileNumber: String)
}