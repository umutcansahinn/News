package com.umutcansahin.mynewsapp.manager.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.umutcansahin.mynewsapp.common.enums.CountryType
import javax.inject.Inject

class LanguageManagerImpl @Inject constructor() : LanguageManager {
    override fun takeLanguageCode(countryCode: String) {
        when (countryCode) {
            CountryType.UNITED_ARAB_EMIRATES.countryCode ->
                setLanguage(CountryType.UNITED_ARAB_EMIRATES.countryCode)

            CountryType.FRANCE.countryCode -> setLanguage(CountryType.FRANCE.countryCode)
            CountryType.GERMANY.countryCode -> setLanguage(CountryType.GERMANY.countryCode)
            CountryType.RUSSIA.countryCode -> setLanguage(CountryType.RUSSIA.countryCode)
            CountryType.TURKEY.countryCode -> setLanguage(CountryType.TURKEY.countryCode)
            else -> setLanguage(CountryType.UNITED_STATE.countryCode)
        }
    }

    override fun getCurrentLanguage(): String {
        return AppCompatDelegate.getApplicationLocales().get(0)?.language ?: "us"
    }

    private fun setLanguage(languageCode: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }
}