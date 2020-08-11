package com.am_development.greenhup.features.details

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.am_development.greenhup.R
import kotlinx.android.synthetic.main.activity_details.*
import kotlin.math.roundToInt


class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val resources = resources


        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height =  ( displayMetrics.heightPixels / (resources.displayMetrics.densityDpi / 160f)).roundToInt()
        val width = (displayMetrics.widthPixels / (resources.displayMetrics.densityDpi / 160f)).roundToInt()

        lout_circle.layoutParams.height= displayMetrics.widthPixels
    }
}
