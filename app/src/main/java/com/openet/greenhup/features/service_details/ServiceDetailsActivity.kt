package com.openet.greenhup.features.service_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.widget.Toast
import androidx.lifecycle.Observer
import com.openet.entities.KEY_SERVICE
import com.openet.entities.ServiceItem
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.core.RequestIntervalHandler2
import com.openet.greenhup.core.isValidEditText
import com.openet.greenhup.core.textValue
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_service_details.*

class ServiceDetailsActivity : BaseActivity(), ServiceDetailsView {

    private lateinit var requestIntervalHandler: RequestIntervalHandler2

    private val presenter: ServiceDetailsPresenter by lazy {
        ServiceDetailsImplPresenter(this, this)
    }

    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> service?.let {service ->
                presenter.requestService(service.id, et_message.text.toString())
            }
        }
    }

    private var service: ServiceItem?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_details)

        //im_outdoor_check.isSelected= true

        setupRequestHandlerView()

        setInitiateValues()

        populateData()

        btn_request.setOnClickListener {
            service?.let {service ->
                if(et_message.isValidEditText())
                    presenter.requestService(service.id, et_message.textValue())
            }

        }
    }

    private fun populateData() {
        service?.let{service ->
            tv_service_name.text= service.name

            Glide.with(this)
                .load(service.imageUrl)
                .into(im_service)

            addHtmlDescriptionToWebView(service.intro)
        }
    }


    companion object {
        fun instantiateIntent(context: Context, service: ServiceItem): Intent {
            return Intent(context, ServiceDetailsActivity::class.java).apply { this.putExtra(
                KEY_SERVICE, service
            ) }
        }
    }

    private fun setInitiateValues() {
        service= intent.getSerializableExtra(KEY_SERVICE) as ServiceItem
    }

    private fun addHtmlDescriptionToWebView(details: String?) {
        val webSettings: WebSettings = web_view_package_details.settings
        webSettings.javaScriptEnabled = true
        webSettings.lightTouchEnabled = true;
        webSettings.javaScriptEnabled = true;
        webSettings.setGeolocationEnabled(true);

        web_view_package_details.loadData(details, "text/html", "UTF-8");
    }


    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, this, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)
    }

    override fun successfulSent() {
        Toast.makeText(this, getString(R.string.successful_sent), Toast.LENGTH_LONG).show()
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
        Toast.makeText(this, message as String, Toast.LENGTH_LONG).show()
    }

}
