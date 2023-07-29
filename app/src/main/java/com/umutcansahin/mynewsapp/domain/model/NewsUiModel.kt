package com.umutcansahin.mynewsapp.domain.model

data class NewsUiModel(
    val article: List<ArticleUiModel>,
    val status: String,
    val totalResults: Int
)