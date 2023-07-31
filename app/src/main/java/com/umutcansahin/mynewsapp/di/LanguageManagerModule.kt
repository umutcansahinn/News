package com.umutcansahin.mynewsapp.di

import com.umutcansahin.mynewsapp.manager.language.LanguageManager
import com.umutcansahin.mynewsapp.manager.language.LanguageManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LanguageManagerModule {

    @Binds
    abstract fun bindLanguageManager(
        languageManagerImpl: LanguageManagerImpl
    ): LanguageManager
}