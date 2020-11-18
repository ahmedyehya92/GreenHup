package com.openet.greenhup.core

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import com.openet.usecases.usecases.TokenUseCase
import java.util.*

public class LocalHelper {
    private val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    private val tokenUseCase= TokenUseCase()
    fun onAttach(context: Context): Context? {
        var lang = ""
        lang = if(tokenUseCase.language.isEmpty())
            getPersistedData(context, Locale.getDefault().language)
        else
            tokenUseCase.language
        return setLocale(context, lang)
    }

    fun onAttach(
        context: Context,
        defaultLanguage: String
    ): Context? {
        persist(context, defaultLanguage)
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context): String? {
        return if(tokenUseCase.language.isEmpty())
            getPersistedData(context, Locale.getDefault().language)
        else
            tokenUseCase.language
    }

    fun setLocale(
        context: Context,
        language: String
    ): Context? {
        persist(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }

    private fun getPersistedData(
        context: Context,
        defaultLanguage: String
    ): String {
        /* SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);*/
        return if(tokenUseCase.language.isEmpty())
            Locale.getDefault().language
        else
            tokenUseCase.language
    }

    private fun persist(
        context: Context,
        language: String
    ) {
        /*   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply(); */
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(
        context: Context,
        language: String
    ): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration =
            context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(
        context: Context,
        language: String
    ): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}