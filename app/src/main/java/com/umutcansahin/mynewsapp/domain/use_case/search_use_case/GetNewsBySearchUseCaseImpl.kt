package com.umutcansahin.mynewsapp.domain.use_case.search_use_case

import com.umutcansahin.mynewsapp.common.Resource
import com.umutcansahin.mynewsapp.domain.model.NewsUiModel
import com.umutcansahin.mynewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsBySearchUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository
) : GetNewsBySearchUseCase {
    override fun invoke(q: String, sortBy: String): Flow<Resource<NewsUiModel>> = flow {
        try {
            emit(Resource.Loading)
            newsRepository.getNewsBySortBy(q, sortBy).apply {
                article.filter { it.urlToImage.isEmpty().not() }
            }.also {
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.orEmpty()))
        }
    }
}