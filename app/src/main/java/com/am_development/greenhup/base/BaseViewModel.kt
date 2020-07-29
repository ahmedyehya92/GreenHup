package com.am_development.greenhup.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am_development.usecases.engine.toMutableLiveData
import com.am_development.usecases.usecases.TokenUseCase

class BaseViewModel (
    private val screenMutableLiveData : MutableLiveData<Int> = (-1).toMutableLiveData(),
    private val tokenUseCase: TokenUseCase = TokenUseCase()
    )
    :ViewModel()
{
    var screenIndicator
        get() = screenMutableLiveData.value
        set(screenValue) = screenMutableLiveData.setValue(screenValue)

    var isLoggedIn
        get() = tokenUseCase.isLoggedIn
        set(value) {tokenUseCase.isLoggedIn = false}
}