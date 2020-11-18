package com.openet.greenhup.features.home

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.openet.usecases.usecases.GetHomeUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeImplPresenter
    (private val view: HomeView,
     private val getHomeUseCase: GetHomeUseCase= GetHomeUseCase(),
     private val disposables: CompositeDisposable = CompositeDisposable()
): HomePresenter, DefaultLifecycleObserver
{
    override fun getHome() {
        view.showLoading()
        getHomeUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.addItemsToGallery(it.home.slider)
                view.addItemsToHorizontalList(it.home.featured)
                view.addItemsToPackagesSlider(it.home.packages)
                it.home.mainoffer?.let {offers ->
                    if(offers.isNotEmpty())
                        view.addMainOffer(offers[0])
                }

                view.addCategories(it.home.categories)

            },{
                Log.e("HomeImplPresenter", "error= ${it.message}")
                view.connectionError()
            })
            .also { disposables.add(it) }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        disposables.dispose()
    }

}