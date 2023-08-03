package com.umutcansahin.mynewsapp.manager.theme

import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManagerImpl @Inject constructor() : ThemeManager {

    override fun setUiTheme(isLight: Boolean) {
        if (isLight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}