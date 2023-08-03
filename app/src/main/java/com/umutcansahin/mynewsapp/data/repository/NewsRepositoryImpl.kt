package com.umutcansahin.mynewsapp.data.repository

import com.umutcansahin.mynewsapp.data.api.NewsApi
import com.umutcansahin.mynewsapp.data.mapper.toNewsUiModel
import com.umutcansahin.mynewsapp.di.IoDispatcher
import com.umutcansahin.mynewsapp.domain.model.NewsUiModel
import com.umutcansahin.mynewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : NewsRepository {
    override suspend fun getAllNews(country: String): NewsUiModel {
        return withContext(ioDispatcher) {
            newsApi.getAllNews(country).toNewsUiModel()
        }
    }

    override suspend fun getNewsBySortBy(q: String, sortByte: String): NewsUiModel {
        return withContext(ioDispatcher) {
            newsApi.getNewsBySearch(q, sortByte).toNewsUiModel()
        }
    }
}