package com.umutcansahin.mynewsapp.data.preference

import kotlinx.coroutines.flow.Flow

interface DataStorePreference {

    suspend fun saveSelectedLanguage(selectedLanguageCode: String)

    val getSelectedLanguage: Flow<String>
}