package com.umutcansahin.mynewsapp.data.api

import com.umutcansahin.mynewsapp.common.Constants.COUNTRY
import com.umutcansahin.mynewsapp.common.Constants.TOP_HEADLINES
import com.umutcansahin.mynewsapp.data.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET(TOP_HEADLINES)
    suspend fun getAllNews(
        @Query(COUNTRY) country: String
    ): NewsResponse
}