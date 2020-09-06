package com.am_development.greenhup.features.services

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.am_development.entities.ServiceItem
import com.am_development.greenhup.R
import com.am_development.greenhup.features.service_details.ServiceDetailsActivity
import kotlinx.android.synthetic.main.activity_services.*

class ServicesActivity : AppCompatActivity(), ServicesView, AdapterServicesList.customButtonListener {

    var adapterServicesList: AdapterServicesList?= null
    val servicesList: MutableList<ServiceItem> = ArrayList()

    private val presenter: ServicesPresenter by lazy {
        ServicesImplPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        adapterServicesList = AdapterServicesList(this, servicesList)
        adapterServicesList?.setCustomButtonListner(this)
        rv_services.adapter= adapterServicesList

        presenter.getServices()
    }

    override fun addServices(servicesList: MutableList<ServiceItem>) {
        adapterServicesList?.addAll(servicesList)
    }

    override fun showLoading() {

    }

    override fun finishLoading() {
    }

    override fun connectionError(message: String?) {
    }

    override fun faildLoading(message: Any) {
    }

    override fun onItemServiceClickListener(plant: ServiceItem) {
        startActivity(Intent(this, ServiceDetailsActivity::class.java))
    }
}
