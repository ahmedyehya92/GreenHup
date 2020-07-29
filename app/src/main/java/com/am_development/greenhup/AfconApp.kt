package com.am_development.greenhup

import android.app.Application
import com.am_development.usecases.Domain

class AfconApp: Application() {
    private lateinit var instance: AfconApp

    override fun onCreate() {
        super.onCreate()
        instance = this
        Domain.integrateWith(this)

    }

    fun getInstance(): AfconApp {
        return instance
    }


}