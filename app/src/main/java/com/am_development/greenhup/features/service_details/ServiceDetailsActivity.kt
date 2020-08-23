package com.am_development.greenhup.features.service_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.am_development.greenhup.R
import kotlinx.android.synthetic.main.activity_service_details.*

class ServiceDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_details)

        im_outdoor_check.isSelected= true
    }
}
