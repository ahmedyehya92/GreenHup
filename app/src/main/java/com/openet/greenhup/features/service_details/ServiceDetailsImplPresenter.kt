package com.openet.greenhup.features.service_details

import android.content.Context
import com.openet.greenhup.R
import com.openet.usecases.usecases.RequestServiceUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class ServiceDetailsImplPresenter (
    private val view: ServiceDetailsView,
    private val context: Context,
    private val requestServiceUseCase: RequestServiceUseCase= RequestServiceUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
): ServiceDetailsPresenter
{
    override fun requestService(serviceId: String, note: String) {
        view.showLoading()

        requestServiceUseCase(serviceId, note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.response.msg == "requested successfully") {
                    view.finishLoading()
                    view.successfulSent()
                }

            },{
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
            }).also { disposables.add(it) }
    }

}