package com.openet.greenhup.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.openet.greenhup.core.LocalHelper
import com.openet.usecases.usecases.TokenUseCase
import java.util.*

open class BaseActivity : AppCompatActivity()
{
    val tokenUseCase= TokenUseCase()
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;


    }

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            if(tokenUseCase.language.isEmpty())
                super.attachBaseContext(
                    LocalHelper().onAttach(
                        it,
                        Locale.getDefault().language
                    )
                )
            else
                super.attachBaseContext(
                    LocalHelper().onAttach(
                        it,
                        tokenUseCase.language
                    )
                )
        }
    }
}