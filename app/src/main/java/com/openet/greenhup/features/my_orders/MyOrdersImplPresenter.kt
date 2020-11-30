package com.openet.greenhup.features.my_orders

import com.openet.usecases.usecases.GetMyOrdersUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MyOrdersImplPresenter (
    private val view: MyOrdersView,
    private val getMyOrdersUseCase: GetMyOrdersUseCase= GetMyOrdersUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
): MyOrdersPresenter {
    override fun getMyOrders() {
        view.showLoading()
        getMyOrdersUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.addOrders(it.response)
            },{
                view.connectionError()
            })
            .also { disposables.add(it) }
    }

}