package com.openet.greenhup.features.contact_us

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.entities.ResponseAboutEnter
import com.openet.greenhup.core.LoadingHandler

interface ContactusView: LoadingHandler {
    fun addDetails(response: ResponseAboutEnter)
}

interface ContactUsPresenter: DefaultLifecycleObserver {
    fun getDetails()
}