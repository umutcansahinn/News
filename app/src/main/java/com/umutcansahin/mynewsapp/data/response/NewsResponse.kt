package com.umutcansahin.mynewsapp.data.response


import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("article")
    val article: List<Article>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?
)