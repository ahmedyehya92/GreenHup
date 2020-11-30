
package com.openet.greenhup.features.my_orders

import android.os.Bundle
import androidx.lifecycle.Observer
import com.openet.entities.Order
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.core.RequestIntervalHandler2
import kotlinx.android.synthetic.main.activity_my_orders.*

class MyOrdersActivity : BaseActivity(), AdapterMyOrdersList.customButtonListener, MyOrdersView {

    var adapterMyOrdersList: AdapterMyOrdersList?= null
    val myOrdersList: MutableList<Order> = ArrayList()

    private lateinit var requestIntervalHandler: RequestIntervalHandler2
    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> presenter.getMyOrders()
        }
    }

    private val presenter: MyOrdersPresenter by lazy {
        MyOrdersImplPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)
        adapterMyOrdersList = AdapterMyOrdersList(this, myOrdersList)
        adapterMyOrdersList?.setCustomButtonListner(this)
        rv_orders.adapter= adapterMyOrdersList

        setupRequestHandlerView()

        presenter.getMyOrders()
    }

    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, this, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)
    }

    override fun onClickPayOnline(orderId: Int, position: Int) {

    }

    override fun addOrders(ordersList: MutableList<Order>) {
        adapterMyOrdersList?.addAll(ordersList)
    }

    override fun showLoading() {
        requestIntervalHandler.showLoadingView(null)
    }

    override fun finishLoading() {
        requestIntervalHandler.finishLoading()
    }

    override fun connectionError(message: String?) {
        requestIntervalHandler.showErrorView(getString(R.string.error_connection))
    }


    override fun faildLoading(message: Any) {
    }
}