package com.openet.greenhup.features.settings

import android.util.Log
import com.openet.greenhup.R
import com.openet.usecases.usecases.GetCountriesUseCase
import com.openet.usecases.usecases.GetUserInfoUseCase
import com.openet.usecases.usecases.UpdateUserInfoUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class SettingsImplPresenter (
    private val view: SettingsView,
    private val getUserInfoUseUseCase: GetUserInfoUseCase= GetUserInfoUseCase(),
    private val getCountriesUseCase: GetCountriesUseCase= GetCountriesUseCase(),
    private val updateUserInfoUseCase: UpdateUserInfoUseCase= UpdateUserInfoUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
): SettingsPresenter
{
    override fun getUserInfo() {
        view.showLoading()
        getUserInfoUseUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.addUserInfo(it.userInfo)

            },{
                view.connectionError()
            }).also { disposables.add(it) }
    }

    override fun getCountries() {
        view.showLoading()
        getCountriesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.addCountries(it.countries)
                getUserInfo()

            },{
                view.connectionError()
            }).also { disposables.add(it) }
    }

    override fun updateUserInfo(
        name: String,
        phone: String,
        country: String,
        city: String,
        address: String,
        ziip: String
    ) {

        view.showLoading()
        updateUserInfoUseCase(name, phone, country, city, address, ziip)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.successfulUpdate()
                getUserInfo()

            },{
                view.connectionError()
                Log.e("SettingsImplPresenter", "error= ${it.cause}")
            }).also { disposables.add(it) }

    }

}