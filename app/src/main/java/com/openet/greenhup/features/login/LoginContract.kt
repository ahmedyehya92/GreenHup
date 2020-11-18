package com.openet.greenhup.features.login

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.greenhup.core.LoadingHandler

interface LoginView: LoadingHandler {
    fun successfulLogin()
    fun failedLogin()
}

interface LoginPresenter: DefaultLifecycleObserver
{
    fun login(email: String, password: String)
}