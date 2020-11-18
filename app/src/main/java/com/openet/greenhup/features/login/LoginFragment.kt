package com.openet.greenhup.features.login

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.openet.greenhup.R
import com.openet.greenhup.core.RequestIntervalHandler2
import com.openet.greenhup.core.isPasswordValid
import com.openet.greenhup.core.isValidEmail
import com.openet.greenhup.core.textValue
import kotlinx.android.synthetic.main.fragment_login.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment(), LoginView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var requestIntervalHandler: RequestIntervalHandler2? = null
    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            //  1 -> categoryId?.let { presenter?.getSubCategories(currentPage, it) }
        }
    }

    private val presenter: LoginPresenter by lazy {
        LoginImplPresenter(this)
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, context!!, false)
        requestIntervalHandler?.tryAgainTrigger?.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler?.setMessageErrorTextColor(R.color.mossy_green)

        btn_login!!.setOnClickListener {

            if(et_email.isValidEmail() && et_password.isPasswordValid())
                presenter.login(et_email.textValue(), et_password.textValue())

            /* fragmentManager!!.beginTransaction()
                 .add(R.id.main_fragment_container, CartFragment(), "cart_fragment")
                 .addToBackStack(null).commit()*/
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun successfulLogin() {


        requireActivity().setResult(Activity.RESULT_OK)
        requireActivity().finish()

        Toast.makeText(context,getString(R.string.successful_login), Toast.LENGTH_LONG).show()
      /*  startActivity(Intent(requireContext(), SplashActivity::class.java))
        finishAffinity(requireActivity())*/
    }

    override fun failedLogin() {
    }

    override fun showLoading() {
        requestIntervalHandler?.showLoadingView()
    }

    override fun finishLoading() {
        requestIntervalHandler?.finishLoading()
    }

    override fun connectionError(message: String?) {
        finishLoading()
        Toast.makeText(context,message?:getString(R.string.unknown_error), Toast.LENGTH_LONG).show()
    }

    override fun faildLoading(message: Any) {
    }

    override fun onStop() {
        presenter.onStop(this)
        super.onStop()
    }
}
