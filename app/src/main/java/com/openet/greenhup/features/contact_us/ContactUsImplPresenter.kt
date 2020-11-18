package com.openet.greenhup.features.contact_us

import android.util.Log
import com.openet.usecases.usecases.GetAboutUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ContactUsImplPresenter (
    private val view: ContactusView,
    private val getAboutUseCase: GetAboutUseCase= GetAboutUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
): ContactUsPresenter {
    override fun getDetails() {
        view.showLoading()
        getAboutUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.addDetails(it.response)
            },{
                Log.e("PlantDetailsActivity", "${it.message}")
                view.connectionError()
            })
            .also { disposables.add(it) }
    }
}