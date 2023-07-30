package com.umutcansahin.mynewsapp.domain.use_case.top_headlines_use_case

import com.umutcansahin.mynewsapp.common.Resource
import com.umutcansahin.mynewsapp.domain.model.NewsUiModel
import com.umutcansahin.mynewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopHeadlinesNewsUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository
) : GetTopHeadlinesNewsUseCase {
    override fun invoke(country: String): Flow<Resource<NewsUiModel>> = flow {
        try {
            emit(Resource.Loading)
            emit(Resource.Success(newsRepository.getAllNews(country)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}