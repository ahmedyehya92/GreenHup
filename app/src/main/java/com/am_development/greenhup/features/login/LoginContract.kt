package com.am_development.greenhup.features.login

import androidx.lifecycle.DefaultLifecycleObserver
import com.am_development.greenhup.core.LoadingHandler

interface LoginView: LoadingHandler {
    fun successfulLogin()
    fun failedLogin()
}

interface LoginPresenter: DefaultLifecycleObserver
{
    fun login(email: String, password: String)
}