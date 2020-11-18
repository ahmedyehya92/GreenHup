package com.openet.greenhup.features.about

import android.util.Log
import com.openet.usecases.usecases.GetAboutUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class AboutImplPresenter(
    private val view: AboutView,
    private val getAboutUseCase: GetAboutUseCase= GetAboutUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
): AboutPresenter {
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