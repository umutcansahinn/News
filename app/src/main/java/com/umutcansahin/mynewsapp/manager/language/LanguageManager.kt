package com.umutcansahin.mynewsapp.manager.language

interface LanguageManager {

    fun takeLanguageCode(countryCode: String)

    fun getCurrentLanguage(): String
}