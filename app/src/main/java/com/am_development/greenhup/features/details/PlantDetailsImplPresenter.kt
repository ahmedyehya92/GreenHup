package com.am_development.greenhup.features.details

import androidx.lifecycle.LifecycleOwner
import com.am_development.usecases.usecases.GetPlantDetailsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PlantDetailsImplPresenter(
    private val view: PlantDetailsView,
    private val getCategoryProductsUseCase: GetPlantDetailsUseCase = GetPlantDetailsUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
) : PlantDetailsPresenter{
    override fun getDetails(plantId: String) {
        view.showLoading()
        getCategoryProductsUseCase(plantId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.addDetails(it.details)
            },{
                view.connectionError()
            })
            .also { disposables.add(it) }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        disposables.dispose()
    }

}