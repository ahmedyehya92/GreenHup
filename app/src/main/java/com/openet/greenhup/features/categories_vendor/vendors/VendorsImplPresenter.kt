package com.openet.greenhup.features.categories_vendor.vendors

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.usecases.usecases.GetVendorsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VendorsImplPresenter (
    private val view: VendorsView,
    private val getVendorsUseCase: GetVendorsUseCase = GetVendorsUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
): VendorsPresenter, DefaultLifecycleObserver {
    override fun getVendors() {
        view.showLoading()
        getVendorsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.addVendors(it.categories)
            },{
                view.connectionError()
            })
            .also { disposables.add(it) }
    }

}