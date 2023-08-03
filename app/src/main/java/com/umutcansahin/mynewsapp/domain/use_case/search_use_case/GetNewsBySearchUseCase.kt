package com.umutcansahin.mynewsapp.domain.use_case.search_use_case

import com.umutcansahin.mynewsapp.common.Resource
import com.umutcansahin.mynewsapp.domain.model.NewsUiModel
import kotlinx.coroutines.flow.Flow

interface GetNewsBySearchUseCase {

    operator fun invoke(q: String, sortBy: String): Flow<Resource<NewsUiModel>>
}