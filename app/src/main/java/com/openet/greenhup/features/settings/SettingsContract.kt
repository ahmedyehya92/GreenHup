package com.openet.greenhup.features.settings

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.entities.Country
import com.openet.entities.UserInfo
import com.openet.greenhup.core.LoadingHandler


interface SettingsView: LoadingHandler{
    fun addUserInfo(userInfo: UserInfo)
    fun addCountries(countriesList: MutableList<Country>)
    fun successfulUpdate()
}

interface SettingsPresenter: DefaultLifecycleObserver{
    fun getUserInfo()
    fun getCountries()
    fun updateUserInfo(name: String,
                       phone: String,
                       country: String,
                       city: String,
                       address: String,
                       ziip: String)
}