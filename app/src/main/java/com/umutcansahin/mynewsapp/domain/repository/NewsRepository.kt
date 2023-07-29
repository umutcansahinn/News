package com.umutcansahin.mynewsapp.domain.repository

import com.umutcansahin.mynewsapp.domain.model.NewsUiModel

interface NewsRepository {

    suspend fun getAllNews(country: String): NewsUiModel
}