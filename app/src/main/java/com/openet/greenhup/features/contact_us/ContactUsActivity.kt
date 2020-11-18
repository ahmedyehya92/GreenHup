package com.openet.greenhup.features.contact_us

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.openet.entities.ResponseAboutEnter
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.core.RequestIntervalHandler2
import kotlinx.android.synthetic.main.activity_contact_us.*


class ContactUsActivity : BaseActivity(), ContactusView {

    private val presenter: ContactUsPresenter by lazy {
        ContactUsImplPresenter(this)
    }

    private lateinit var requestIntervalHandler: RequestIntervalHandler2

    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> presenter.getDetails()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        setupRequestHandlerView()
        presenter.getDetails()
    }

    override fun addDetails(response: ResponseAboutEnter) {
        tv_email.text= response.contact.email
        tv_phone.text= response.contact.phone
        tv_address.text= response.contact.address

        btn_show_location.visibility= View.GONE

        btn_show_location.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:${response.contact.latitude},${response.contact.longitude}")

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
// Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps")

// Attempt to start an activity that can handle the Intent
            startActivity(mapIntent)

        }
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

        if(message == "You are not logged in!")
            requestIntervalHandler.showNotLoggedInView()
        else
            Toast.makeText(this, message as String, Toast.LENGTH_LONG).show()
    }

    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, this, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)
    }
}