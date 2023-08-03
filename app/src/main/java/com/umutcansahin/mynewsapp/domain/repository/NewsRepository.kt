package com.umutcansahin.mynewsapp.domain.repository

import com.umutcansahin.mynewsapp.common.enums.SortBy
import com.umutcansahin.mynewsapp.domain.model.NewsUiModel

interface NewsRepository {

    suspend fun getAllNews(country: String): NewsUiModel

    suspend fun getNewsBySortBy(q: String, sortByte: String):NewsUiModel
}