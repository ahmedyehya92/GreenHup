package com.openet.greenhup.features.cart

import android.content.Context
import android.util.Log
import com.openet.greenhup.R
import com.openet.usecases.usecases.AddProductToCartUseCase
import com.openet.usecases.usecases.GetCartItemsUseCase
import com.openet.usecases.usecases.StatePackageToCartUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class CartImplPresenter (
    private val view: CartView,
    private val context: Context,
    private val getCartItemsUseCase: GetCartItemsUseCase = GetCartItemsUseCase(),
    private val addProductToCartUseCase: AddProductToCartUseCase= AddProductToCartUseCase(),
    private val statePackageToCartUseCase: StatePackageToCartUseCase= StatePackageToCartUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
): CartPresenter {
    override fun getCart() {
        view.showLoading()
        getCartItemsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.addToCartList(it.cartItems)
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
            })
            .also { disposables.add(it) }
    }

    override fun changeCartItemAmount(id: String, position: Int, newQuantity: Int, type: String) {
        view.showLoading()

        if(type=="product")
        {
            addProductToCartUseCase(id,newQuantity.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.finishLoading()
                    view.changeItemAmount(position, newQuantity)
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

                })
                .also { disposables.add(it) }
        }
        else if(type== "package")
        {
            statePackageToCartUseCase.invoke(id,newQuantity.toString(), false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.finishLoading()
                    view.changeItemAmount(position, newQuantity)
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

                })
                .also { disposables.add(it) }
        }


    }

}