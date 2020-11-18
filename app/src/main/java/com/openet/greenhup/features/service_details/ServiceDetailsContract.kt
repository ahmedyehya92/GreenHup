package com.openet.greenhup.features.service_details

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.greenhup.core.LoadingHandler

interface ServiceDetailsView: LoadingHandler
{
    fun successfulSent()
}

interface ServiceDetailsPresenter: DefaultLifecycleObserver
{
    fun requestService(serviceId: String, note: String)
}