package com.openet.greenhup.features.splash

import android.content.Intent
import android.os.Bundle
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.features.main.MainActivity
import com.openet.greenhup.features.on_boarding.OnBoardingActivity
import com.openet.usecases.usecases.TokenUseCase
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val timer = Timer("SettingUp", false)
        timer?.schedule(2000) {
            val tokenUseCase= TokenUseCase()
            if(tokenUseCase.isFirstTime) {
                startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
                tokenUseCase.isFirstTime= false
            }

            else {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
            finish()
        }
    }
}
