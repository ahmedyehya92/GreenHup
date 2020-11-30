package com.openet.greenhup.features.my_orders

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.entities.Order
import com.openet.greenhup.core.LoadingHandler

interface MyOrdersView: LoadingHandler {
    fun addOrders(ordersList: MutableList<Order>)
}

interface MyOrdersPresenter: DefaultLifecycleObserver{
    fun getMyOrders()
}