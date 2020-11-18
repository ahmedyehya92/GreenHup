package com.openet.greenhup.features.details

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.openet.greenhup.R
import com.openet.greenhup.features.cart.CartImplPresenter
import com.openet.usecases.usecases.AddProductToCartUseCase
import com.openet.usecases.usecases.FavoriteUseCase
import com.openet.usecases.usecases.GetPlantDetailsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class PlantDetailsImplPresenter(
    private val view: PlantDetailsView,
    private val context: Context,
    private val getCategoryProductsUseCase: GetPlantDetailsUseCase = GetPlantDetailsUseCase(),
    private val favoriteUseCase: FavoriteUseCase= FavoriteUseCase(),
    private val addProductToCartUseCase: AddProductToCartUseCase= AddProductToCartUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
) : PlantDetailsPresenter{
    override fun getDetails(plantId: String) {
        view.showLoading()
        getCategoryProductsUseCase(plantId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.addDetails(it.details)
            },{
                Log.e("PlantDetailsActivity", "${it.message}")
                view.connectionError()
            })
            .also { disposables.add(it) }
    }

    override fun changeCartItemAmount(id: String, newQuantity: Int) {
        view.showLoading()
        addProductToCartUseCase(id,newQuantity.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.changeItemAmount(newQuantity)
            },{
                Log.e(PlantDetailsImplPresenter::class.java.simpleName, "error= ${it.message}")
                Log.e(CartImplPresenter::class.java.simpleName, "error code= ${it.message}")
                when(it)
                {
                    is HttpException -> {
                        when(it.code())
                        {
                            401 ->{
                                view.faildLoading(context.getString(R.string.you_are_not_logged_in))
                            }
                            else -> view.faildLoading(context.getString(R.string.unknown_error))
                        }
                    }

                    else -> view.faildLoading(context.getString(R.string.error_connection))
                }
            })
            .also { disposables.add(it) }
    }

    override fun changeFavoriteStateProduct(productId: String, favorite: Boolean) {
        view.showLoading()
        favoriteUseCase.invoke(productId, favorite)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.changeFavoriteState(!favorite)
            },{
                Log.e(PlantDetailsImplPresenter::class.java.simpleName, "error= ${it.message}")
                when(it)
                {
                    is HttpException -> {
                        when(it.code())
                        {
                            401 ->{
                                view.faildLoading(context.getString(R.string.you_are_not_logged_in))
                            }
                            else -> view.faildLoading(context.getString(R.string.unknown_error))
                        }
                    }

                    else -> view.faildLoading(context.getString(R.string.error_connection))
                }
            })
            .also { disposables.add(it) }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        disposables.dispose()
    }

}