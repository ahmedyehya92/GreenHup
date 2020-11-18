package com.openet.greenhup.features.save_order

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.openet.entities.ResponseSaveOrderEntry
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.core.RequestIntervalHandler2
import com.openet.greenhup.core.isValidEditText
import com.openet.greenhup.core.textValue
import com.openet.greenhup.features.pay.PayActivity
import kotlinx.android.synthetic.main.activity_save_order.*

class SaveOrderActivity : BaseActivity(), SaveOrderView {
    private lateinit var requestIntervalHandler: RequestIntervalHandler2

    private val presenter: SaveOrderPresenter by lazy {
        SaveOrderImplPresenter(this, this)
    }

    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> presenter.saveOrder(et_comments.textValue(), et_name.textValue(), et_phone.textValue(), et_email.textValue(), et_address.textValue())
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_save_order)
        super.onCreate(savedInstanceState)

        setupRequestHandlerView()

        btn_check_out.setOnClickListener {
            if(et_name.isValidEditText() && et_email.isValidEditText() && et_phone.isValidEditText() && et_address.isValidEditText())
                if(et_comments.textValue().isEmpty())
                    presenter.saveOrder("no comments", et_name.textValue(), et_phone.textValue(), et_email.textValue(), et_address.textValue())

                else
                    presenter.saveOrder(et_comments.textValue(), et_name.textValue(), et_phone.textValue(), et_email.textValue(), et_address.textValue())
        }
    }

    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, this, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)
    }

    override fun successfulSaveOrder(response: ResponseSaveOrderEntry) {
        startActivity(PayActivity.instantiateIntent(this, response))
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