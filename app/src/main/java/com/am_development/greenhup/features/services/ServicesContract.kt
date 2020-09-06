package com.am_development.greenhup.features.services

import com.am_development.entities.ServiceItem
import com.am_development.greenhup.core.LoadingHandler

interface ServicesView: LoadingHandler {
    fun addServices(servicesList: MutableList<ServiceItem>)
}

interface ServicesPresenter{
    fun getServices()
}