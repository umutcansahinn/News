package com.umutcansahin.mynewsapp.domain.use_case.top_headlines_use_case

import com.umutcansahin.mynewsapp.common.Resource
import com.umutcansahin.mynewsapp.domain.model.NewsUiModel
import kotlinx.coroutines.flow.Flow

interface GetTopHeadlinesNewsUseCase {

    operator fun invoke(country: String): Flow<Resource<NewsUiModel>>
}