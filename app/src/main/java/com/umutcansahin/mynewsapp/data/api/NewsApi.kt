package com.umutcansahin.mynewsapp.data.api

import com.umutcansahin.mynewsapp.common.Constants.COUNTRY
import com.umutcansahin.mynewsapp.common.Constants.EVERYTHING
import com.umutcansahin.mynewsapp.common.Constants.SEARCH_Q
import com.umutcansahin.mynewsapp.common.Constants.SORT_BY
import com.umutcansahin.mynewsapp.common.Constants.TOP_HEADLINES
import com.umutcansahin.mynewsapp.data.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET(TOP_HEADLINES)
    suspend fun getAllNews(
        @Query(COUNTRY) country: String
    ): NewsResponse

    @GET(EVERYTHING)
    suspend fun getNewsBySearch(
        @Query(SEARCH_Q) searchKey: String,
        @Query(SORT_BY) sortBy: String
    ): NewsResponse
}