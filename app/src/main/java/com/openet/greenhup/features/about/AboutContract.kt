package com.openet.greenhup.features.about

import androidx.lifecycle.DefaultLifecycleObserver
import com.openet.entities.ResponseAboutEnter
import com.openet.greenhup.core.LoadingHandler

interface AboutView: LoadingHandler {
    fun addDetails(response: ResponseAboutEnter)
}

interface AboutPresenter: DefaultLifecycleObserver {
    fun getDetails()
}