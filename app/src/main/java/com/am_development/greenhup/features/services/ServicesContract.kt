package com.am_development.greenhup.features.services

import com.am_development.entities.ServiceItem

interface ServicesView{
    fun addServices(servicesList: MutableList<ServiceItem>)
}