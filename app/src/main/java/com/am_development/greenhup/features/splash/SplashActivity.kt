package com.am_development.greenhup.features.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.am_development.greenhup.R
import com.am_development.greenhup.features.main.MainActivity
import com.am_development.greenhup.features.on_boarding.OnBoardingActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val timer = Timer("SettingUp", false)
        timer?.schedule(2000) {
            startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
            finish()
        }
    }
}
