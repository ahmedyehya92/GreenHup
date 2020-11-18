package com.openet.greenhup.features.save_order

import android.content.Context
import com.openet.greenhup.R
import com.openet.usecases.usecases.SaveOrderUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class SaveOrderImplPresenter (
    private val view: SaveOrderView,
    private val context: Context,
    private val saveOrderUseCase: SaveOrderUseCase= SaveOrderUseCase(),
    private val disposables: CompositeDisposable = CompositeDisposable()
): SaveOrderPresenter
{
    override fun saveOrder(
        comments: String,
        name: String,
        phone: String,
        email: String,
        address: String
    ) {
        view.showLoading()
        saveOrderUseCase(comments, name, phone, email, address)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.response.msg == "order created successfully") {
                    view.finishLoading()
                    view.successfulSaveOrder(it.response)
                }

                else
                {
                    view.connectionError()
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