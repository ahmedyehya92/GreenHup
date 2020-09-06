package com.am_development.greenhup.features.services

import androidx.lifecycle.DefaultLifecycleObserver
import com.am_development.usecases.usecases.GetServicesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ServicesImplPresenter (
    private val view: ServicesView,
    private val getServicesUseCase: GetServicesUseCase= GetServicesUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
): ServicesPresenter, DefaultLifecycleObserver
{
    override fun getServices() {
        view.showLoading()
        getServicesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
               view.addServices(it.services)
            },{
                view.connectionError()
            })
            .also { disposables.add(it) }
    }

}