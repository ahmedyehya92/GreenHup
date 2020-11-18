package com.openet.greenhup.features.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.openet.greenhup.R
import com.openet.greenhup.core.*
import kotlinx.android.synthetic.main.fragment_register.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment(), RegisterView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var customListener: customButtonListener? = null
    private lateinit var requestIntervalHandler: RequestIntervalHandler2
    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            //  1 -> categoryId?.let { presenter?.getSubCategories(currentPage, it) }
        }
    }

    private val presenter: RegisterPresenter by lazy {
        RegisterImplPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, context!!, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.mossy_green)

        btn_register.setOnClickListener {
            if(et_name.isValidEditText() &&
                et_email.isValidEmail() &&
                et_password.isPasswordValid() &&
                et_confirm_password.isConfirmPasswordValid(et_password.textValue()))
                //&& et_phone.isValidEditText())

                presenter.register(et_name.textValue(), et_email.textValue(), et_password.textValue(), et_confirm_password.textValue(), "0")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun successfulRegister() {
        customListener?.onSuccessRegister()
    }

    override fun failedRegister() {

    }

    override fun showLoading() {
        requestIntervalHandler.showLoadingView()
    }

    override fun finishLoading() {
        requestIntervalHandler.finishLoading()
    }

    override fun connectionError(message: String?) {
        Toast.makeText(context,message?:getString(R.string.unknown_error), Toast.LENGTH_LONG).show()

    }

    override fun faildLoading(message: Any) {

    }

    override fun onDestroy() {
        presenter.onDestroy(this)
        super.onDestroy()
    }

    interface customButtonListener {
        fun onSuccessRegister()
    }

    fun setCustomButtonListner(listener: customButtonListener?) {
        customListener = listener
    }

}
