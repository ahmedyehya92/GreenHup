package com.openet.greenhup.features.about

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.webkit.WebSettings
import android.widget.Toast
import androidx.lifecycle.Observer
import com.openet.entities.ResponseAboutEnter
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.core.RequestIntervalHandler2
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseActivity(), AboutView {

    private val presenter: AboutPresenter by lazy {
        AboutImplPresenter(this)
    }

    private lateinit var requestIntervalHandler: RequestIntervalHandler2

    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> presenter.getDetails()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setupRequestHandlerView()
        presenter.getDetails()

    }

    override fun addDetails(response: ResponseAboutEnter) {
        addHtmlDescriptionToWebView(response.about)
    }

    private fun addHtmlDescriptionToWebView(about: String) {
     /*           val webSettings: WebSettings = web_view_about.settings
                webSettings.javaScriptEnabled = true
                webSettings.lightTouchEnabled = true;
                webSettings.javaScriptEnabled = true;
                webSettings.setGeolocationEnabled(true);

        web_view_about.loadData(about, "text/html", "UTF-8")*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            web_view_about.setText(Html.fromHtml(about, Html.FROM_HTML_MODE_COMPACT));
        } else {
            web_view_about.setText(Html.fromHtml(about));
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