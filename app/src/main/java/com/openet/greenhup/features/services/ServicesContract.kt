package com.openet.greenhup.features.services

import com.openet.entities.ServiceItem
import com.openet.greenhup.core.LoadingHandler

interface ServicesView: LoadingHandler {
    fun addServices(servicesList: MutableList<ServiceItem>)
}

interface ServicesPresenter{
    fun getServices()
}