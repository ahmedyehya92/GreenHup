package com.openet.greenhup.features.package_details

import android.content.Context
import android.util.Log
import com.openet.greenhup.R
import com.openet.greenhup.features.details.PlantDetailsImplPresenter
import com.openet.usecases.usecases.GetPackageDetailsUseCase
import com.openet.usecases.usecases.StatePackageToCartUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class PackageDetailsImplPresenter (
    private val view: PackageDetailsView,
    private val context: Context,
    private val getPackageDetailsUseCase: GetPackageDetailsUseCase = GetPackageDetailsUseCase(),
    private val statePackageToCartUseCase: StatePackageToCartUseCase= StatePackageToCartUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
): PackageDetailsPresenter {
    override fun getPackageDetails(packageId: String) {
        view.showLoading()
        getPackageDetailsUseCase(packageId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.addPackageDetails(it.packageDetails)
                view.finishLoading()
            },{
                Log.e("PlantDetailsActivity", "${it.message}")
                view.connectionError()
            })
            .also { disposables.add(it) }
    }

    override fun changeCartStae(packageId: String, added: Boolean, quantity: String) {
        view.showLoading()
        statePackageToCartUseCase.invoke(packageId, quantity, added)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.finishLoading()
                view.changeAddToCartState(!added)
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

}