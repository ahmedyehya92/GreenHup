package com.openet.greenhup.features.favorites


import android.content.Context
import android.util.Log
import com.openet.greenhup.R
import com.openet.greenhup.features.cart.CartImplPresenter
import com.openet.usecases.usecases.GetUserFavoritesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class FavoritesImplPresenter (
    private val view: FavoritesView,
    private val context: Context,
    private val getUserFavoritesUseCase: GetUserFavoritesUseCase = GetUserFavoritesUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable(),
    private var pageSc: Int = 0
): FavoritesPresenter {
    override fun getFavorites(page: Int) {
        view.setRequestIsFinished(false)
        this.pageSc = page
        if (page == 1)
            view.showLoading()

        getUserFavoritesUseCase(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (pageSc != 1) {
                    view.removeLoadingFooter()
                    view.setIsLoadingFalse()
                    Log.d("", "page not equal 1: ")
                }

                if (pageSc != 1 && it.favouritesList.size == 0) run {
                    view.setLastPageTrue()
                    view.removeLoadingFooter()
                    Log.d("", "last page")
                    Log.d("", "page: " + pageSc)

                }
                else
                    run {

                        if (pageSc == 1) {
                            if (it.favouritesList.size == 0)
                                view.showEmptyViewForList()
                            else
                                view.finishLoading()
                        }
                        view.addPlants(it.favouritesList)
                        /*if (it.pagination.pageCount > 1)
                            view.addLoadingFooter()
                         */
                    }
                view.setRequestIsFinished(true)
            },{
                Log.e(CartImplPresenter::class.java.simpleName, "error code= ${it.message}")
                when(it)
                {
                    is HttpException -> {
                        when(it.code())
                        {
                            401 ->{
                                view.faildLoading(context.getString(R.string.you_must_login))
                            }
                            else -> view.connectionError()
                        }
                    }

                    else -> view.connectionError()
                }
                view.setRequestIsFinished(true)
            })
            .also { disposables.add(it) }
    }

}