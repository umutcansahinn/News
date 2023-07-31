package com.umutcansahin.mynewsapp.di

import com.umutcansahin.mynewsapp.data.preference.DataStorePreference
import com.umutcansahin.mynewsapp.data.preference.DataStorePreferenceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    abstract fun bindDataStorePreference(
        dataStorePreferenceImpl: DataStorePreferenceImpl
    ): DataStorePreference
}