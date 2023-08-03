package com.umutcansahin.mynewsapp.di

import com.umutcansahin.mynewsapp.manager.theme.ThemeManager
import com.umutcansahin.mynewsapp.manager.theme.ThemeManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ThemeModule {

    @Binds
    abstract fun bindThemeManager(
        themeManagerImpl: ThemeManagerImpl
    ): ThemeManager
}