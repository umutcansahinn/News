package com.umutcansahin.mynewsapp.di

import com.umutcansahin.mynewsapp.domain.use_case.search_use_case.GetNewsBySearchUseCase
import com.umutcansahin.mynewsapp.domain.use_case.search_use_case.GetNewsBySearchUseCaseImpl
import com.umutcansahin.mynewsapp.domain.use_case.top_headlines_use_case.GetTopHeadlinesNewsUseCase
import com.umutcansahin.mynewsapp.domain.use_case.top_headlines_use_case.GetTopHeadlinesNewsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetTopHeadlinesNewsUseCase(
        getTopHeadlinesNewsUseCaseImpl: GetTopHeadlinesNewsUseCaseImpl
    ): GetTopHeadlinesNewsUseCase

    @Binds
    abstract fun bindGetNewsBySearchUseCase(
        getNewsBySearchUseCaseImpl: GetNewsBySearchUseCaseImpl
    ): GetNewsBySearchUseCase
}