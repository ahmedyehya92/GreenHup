package com.openet.greenhup.features.services

import android.os.Bundle
import androidx.lifecycle.Observer
import com.openet.entities.ServiceItem
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.core.RequestIntervalHandler2
import com.openet.greenhup.features.service_details.ServiceDetailsActivity
import kotlinx.android.synthetic.main.activity_services.*
import kotlinx.android.synthetic.main.activity_services.lout_loading_interval_view_container

class ServicesActivity : BaseActivity(), ServicesView, AdapterServicesList.customButtonListener {

    var adapterServicesList: AdapterServicesList?= null
    val servicesList: MutableList<ServiceItem> = ArrayList()

    private lateinit var requestIntervalHandler: RequestIntervalHandler2
    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> presenter.getServices()
        }
    }


    private val presenter: ServicesPresenter by lazy {
        ServicesImplPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        adapterServicesList = AdapterServicesList(this, servicesList)
        adapterServicesList?.setCustomButtonListner(this)
        rv_services.adapter= adapterServicesList

        setupRequestHandlerView()

        presenter.getServices()
    }


    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, this, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)
    }


    override fun addServices(servicesList: MutableList<ServiceItem>) {
        adapterServicesList?.addAll(servicesList)
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

    override fun onItemServiceClickListener(service: ServiceItem) {
        startActivity(ServiceDetailsActivity.instantiateIntent(this, service))


    }
}
