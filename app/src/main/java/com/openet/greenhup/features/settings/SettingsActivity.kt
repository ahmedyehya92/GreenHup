package com.openet.greenhup.features.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.openet.entities.Country
import com.openet.entities.UserInfo
import com.openet.greenhup.R
import com.openet.greenhup.core.RequestIntervalHandler2
import com.openet.greenhup.core.textValue
import com.openet.greenhup.features.splash.SplashActivity
import com.openet.usecases.usecases.TokenUseCase
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.lout_loading_interval_view_container

class SettingsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, SettingsView {
    val tokenUseCase= TokenUseCase()
    var lang= ""
    var countryId=""
    private var adapter: SpinnerCountriesAdapter? = null
    private lateinit var requestIntervalHandler: RequestIntervalHandler2
    private val presenter: SettingsPresenter by lazy {
        SettingsImplPresenter(this)
    }

    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> presenter.getUserInfo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupRequestHandlerView()

        lang = tokenUseCase.language

        if (tokenUseCase.isLoggedIn)
        {
            presenter.getCountries()
        }
        else
        {
            lout_data.visibility= View.GONE
        }

        spinner.onItemSelectedListener = this
        spinner_countries.onItemSelectedListener= this

        val categories: MutableList<String> = ArrayList()
        categories.add(getString(R.string.english))
        categories.add(getString(R.string.arabic))

        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.spinner_list_default, categories)

        // Drop down layout style - list view with radio button

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_list_default)

        // attaching data adapter to spinner

        // attaching data adapter to spinner

        spinner.adapter = dataAdapter

        when {
            tokenUseCase.language.isEmpty() -> spinner.setSelection(0)
            tokenUseCase.language=="en" -> spinner.setSelection(0)
            tokenUseCase.language=="ar" -> spinner.setSelection(1)
        }

        btn_save.setOnClickListener {
            if(tokenUseCase.isLoggedIn)
            {
                Log.e("Settings", "country= $countryId")
                Log.e("Settings", "et_name= ${et_name.textValue()}")
                Log.e("Settings", "et_state= ${et_state.textValue()}")
                Log.e("Settings", "et_address= ${et_address.textValue()}")
                presenter.updateUserInfo(et_name.textValue(), "0", countryId, et_state.textValue(), et_address.textValue(),"0")
            }
            else
            {
                tokenUseCase.language= lang
                Log.e("SettingsActivity", "lang= ${tokenUseCase.language}")
                val intent =
                    Intent(this, SplashActivity::class.java)
                this.startActivity(intent)
                finishAffinity()
            }
        }
    }



    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            when (position) {
                0 -> lang = "en"
                1 -> lang = "ar"
            }

      /*  else if(view== spinner_countries)
        {

        }*/


    }


    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, this, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)
    }

    override fun addUserInfo(userInfo: UserInfo) {
        lout_data.visibility= View.VISIBLE
        et_name.setText(userInfo.name)
        et_address.setText(userInfo.address)
        et_email.setText(userInfo.email)
        et_state.setText(userInfo.city)
        countryId= userInfo.country
        adapter?.getCountryPosition(userInfo.country)?.let { spinner_countries.setSelection(it) }
        //et_country.setText(userInfo.country)

    }



    override fun addCountries(countriesList: MutableList<Country>) {
        adapter = SpinnerCountriesAdapter(
            this,
            android.R.layout.simple_spinner_item,
            countriesList.toTypedArray()
        )

        spinner_countries.adapter= adapter

        spinner_countries.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                adapter?.let { countryId= it.getItem(position).id }
            }

        }
    }


    override fun successfulUpdate() {
        Toast.makeText(this, getString(R.string.successful_update), Toast.LENGTH_LONG).show()
        tokenUseCase.language= lang
        Log.e("SettingsActivity", "lang= ${tokenUseCase.language}")
        val intent =
            Intent(this, SplashActivity::class.java)
        this.startActivity(intent)
        finishAffinity()
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