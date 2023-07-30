package com.umutcansahin.mynewsapp.data.mapper

import com.umutcansahin.mynewsapp.data.response.Article
import com.umutcansahin.mynewsapp.data.response.NewsResponse
import com.umutcansahin.mynewsapp.data.response.Source
import com.umutcansahin.mynewsapp.domain.model.ArticleUiModel
import com.umutcansahin.mynewsapp.domain.model.NewsUiModel
import com.umutcansahin.mynewsapp.domain.model.SourceUiModel

fun Source.toSourceUiModel(): SourceUiModel {
    return SourceUiModel(id = id.orEmpty(), name = name.orEmpty())
}

fun Article.toArticleUiModel(): ArticleUiModel {
    return ArticleUiModel(
        author = author.orEmpty(),
        content = content.orEmpty(),
        description = description.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        source = source.toSourceUiModel(),
        title = title.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage.orEmpty()
    )
}

fun NewsResponse.toNewsUiModel(): NewsUiModel {
    return NewsUiModel(
//        article = article?.let { it.map { it.toArticleUiModel() } } ?: emptyList(),
        article = article.orEmptyList().map { it.toArticleUiModel() },
        status = status.orEmpty(),
        totalResults = totalResults.orMinusOne()
    )
}

fun <T> List<T>?.orEmptyList(): List<T> {
    return this ?: emptyList()
}

fun Int?.orMinusOne(): Int {
    return this ?: -1
}