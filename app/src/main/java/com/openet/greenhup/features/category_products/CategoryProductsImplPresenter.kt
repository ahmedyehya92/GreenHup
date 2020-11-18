package com.openet.greenhup.features.category_products

import android.util.Log
import com.openet.usecases.usecases.GetCategoryProductsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CategoryProductsImplPresenter (
    private val view: CategoryProductsView,
    private val getCategoryProductsUseCase: GetCategoryProductsUseCase= GetCategoryProductsUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable(),
    private var pageSc: Int = 0
): CategoryProductsPresenter {
    override fun getPlantsList(categoryId: String, page: Int) {
        view.setRequestIsFinished(false)
        this.pageSc = page
        if (page == 1)
            view.showLoading()

        getCategoryProductsUseCase(categoryId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (pageSc != 1) {
                    view.removeLoadingFooter()
                    view.setIsLoadingFalse()
                    Log.d("", "page not equal 1: ")
                }

                if (pageSc != 1 && it.categoryProducts.size == 0) run {
                    view.setLastPageTrue()
                    view.removeLoadingFooter()
                    Log.d("", "last page")
                    Log.d("", "page: " + pageSc)

                }
                else
                    run {

                        if (pageSc == 1) {
                            if (it.categoryProducts.size == 0)
                                view.showEmptyViewForList()
                            else
                                view.finishLoading()
                        }
                        view.addPlants(it.categoryProducts)
                        /*if (it.pagination.pageCount > 1)
                            view.addLoadingFooter()
                         */
                    }
                view.setRequestIsFinished(true)
            },{
                view.connectionError(it.message)
                view.setRequestIsFinished(true)
            })
            .also { disposables.add(it) }
    }
}