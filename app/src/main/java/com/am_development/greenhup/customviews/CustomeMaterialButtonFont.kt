package com.am_development.greenhup.customviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.am_development.greenhup.R
import com.am_development.greenhup.core.FontCache
import com.google.android.material.button.MaterialButton

class CustomeMaterialButtonFont : MaterialButton {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        applyCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {

        applyCustomFont(context, attrs)
    }

    private fun applyCustomFont(context: Context, attrs: AttributeSet) {
        val attributeArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomFontTextView
        )

        val fontName = attributeArray.getString(R.styleable.CustomFontTextView_custom_font)
        val textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL)

        val customFont = selectTypeface(context, fontName!!, textStyle)
        typeface = customFont

        attributeArray.recycle()
    }

    private fun selectTypeface(context: Context, fontName: String, textStyle: Int): Typeface? {
        return try {
            FontCache("fonts/$fontName.ttf", context).typeface
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }

    companion object {
        const val ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android"
    }
}