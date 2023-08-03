package com.umutcansahin.mynewsapp.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.umutcansahin.mynewsapp.common.Constants.DATASTORE_LANGUAGE_KEY
import com.umutcansahin.mynewsapp.common.Constants.DATASTORE_NAME
import com.umutcansahin.mynewsapp.common.Constants.DATASTORE_THEME_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    name = DATASTORE_NAME
)

class DataStorePreferenceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStorePreference {

    companion object {
        private val SELECTED_LANGUAGE_CODE = stringPreferencesKey(DATASTORE_LANGUAGE_KEY)
        private val SELECTED_THEME_TYPE = booleanPreferencesKey(DATASTORE_THEME_KEY)
    }

    override suspend fun saveSelectedLanguage(selectedLanguageCode: String) {
        context.datastore.edit { preferences ->
            preferences[SELECTED_LANGUAGE_CODE] = selectedLanguageCode
        }
    }

    override val getSelectedLanguage: Flow<String> = context.datastore.data.map { preferences ->
        preferences[SELECTED_LANGUAGE_CODE] ?: "us"
    }

    override suspend fun saveDarkModeEnabled(isDarkModeEnabled: Boolean) {
        context.datastore.edit { preferences ->
            preferences[SELECTED_THEME_TYPE] = isDarkModeEnabled
        }
    }

    override val isDarkModeEnabled: Flow<Boolean> = context.datastore.data.map { preferences ->
        preferences[SELECTED_THEME_TYPE] ?: true
    }
}