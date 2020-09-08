package com.am_development.greenhup.features.categories_vendor

import androidx.lifecycle.DefaultLifecycleObserver
import com.am_development.usecases.usecases.GetCategoriesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CategoriesImplPresenter (
    private val view: CategoriesView,
    private val getCategoriesUseCase: GetCategoriesUseCase = GetCategoriesUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
): CategoriesPresenter, DefaultLifecycleObserver {
    override fun getCategories() {
        view.showLoading()
        getCategoriesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.addCategories(it.categories)
            },{
                view.connectionError()
            })
            .also { disposables.add(it) }
    }
}