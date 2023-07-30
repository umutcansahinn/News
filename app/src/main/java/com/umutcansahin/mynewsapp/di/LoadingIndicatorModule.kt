package com.umutcansahin.mynewsapp.di

import com.umutcansahin.mynewsapp.manager.loading_indicator.LoadingIndicator
import com.umutcansahin.mynewsapp.manager.loading_indicator.LoadingIndicatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class LoadingIndicatorModule {

    @Binds
    abstract fun bindLoadingIndicator(
        loadingIndicatorImpl: LoadingIndicatorImpl
    ):LoadingIndicator
}