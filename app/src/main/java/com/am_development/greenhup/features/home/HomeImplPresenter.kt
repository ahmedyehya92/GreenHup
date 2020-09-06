package com.am_development.greenhup.features.home

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.am_development.greenhup.core.LoadingHandler
import com.am_development.usecases.usecases.GetHomeUseCase
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
                view.addMainOffer(it.home.mainoffer[0])
            },{
                view.connectionError()
            })
            .also { disposables.add(it) }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        disposables.dispose()
    }

}