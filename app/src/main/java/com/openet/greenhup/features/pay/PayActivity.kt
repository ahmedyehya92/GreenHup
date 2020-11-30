package com.openet.greenhup.features.pay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.openet.entities.KEY_RESPONSE_SAVE_ORDER
import com.openet.entities.ResponseSaveOrderEntry
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pay.*

class PayActivity : BaseActivity() {
    private var response: ResponseSaveOrderEntry?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        setInitiateValues()
        setupWebView()

    }

    private fun setupWebView() {
        val webSettings: WebSettings = web_view_pay.settings
        webSettings.javaScriptEnabled = true
        webSettings.lightTouchEnabled = true
        webSettings.javaScriptEnabled = true
        webSettings.setGeolocationEnabled(true)
        response?.let {response ->
            //web_view_pay.loadUrl("https://www.api.greenhub.shop/api/gopay/${response.order_id}/${response.order_reference}/${response.gtotal}")
            web_view_pay.loadUrl("https://api.greenhub.shop/api/gopayvisa/${response.order_id}")
        }
        web_view_pay.setWebViewClient(object: WebViewClient() {})
    }

    companion object {
        fun instantiateIntent(context: Context, response: ResponseSaveOrderEntry): Intent {
            return Intent(context, PayActivity::class.java).apply { this.putExtra(
                KEY_RESPONSE_SAVE_ORDER, response
            ) }
        }
    }

    private fun setInitiateValues() {
        response= intent.getSerializableExtra(KEY_RESPONSE_SAVE_ORDER) as ResponseSaveOrderEntry
    }
}