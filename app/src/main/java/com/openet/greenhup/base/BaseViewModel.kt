package com.openet.greenhup.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openet.usecases.engine.toMutableLiveData
import com.openet.usecases.usecases.TokenUseCase

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