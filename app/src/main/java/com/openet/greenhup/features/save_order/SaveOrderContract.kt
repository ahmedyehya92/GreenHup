package com.openet.greenhup.features.save_order

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.entities.ResponseSaveOrderEntry
import com.openet.greenhup.core.LoadingHandler

interface SaveOrderView: LoadingHandler{
    fun successfulSaveOrder(response: ResponseSaveOrderEntry)
}

interface SaveOrderPresenter: DefaultLifecycleObserver
{
    fun saveOrder(comments: String, name: String, phone: String, email: String, address: String)
}