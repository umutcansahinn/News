package com.umutcansahin.mynewsapp.data.api

import com.google.common.truth.Truth.assertThat
import com.umutcansahin.mynewsapp.COUNTRY_CODE
import com.umutcansahin.mynewsapp.EVERYTHING_TEST
import com.umutcansahin.mynewsapp.SEARCH_Q
import com.umutcansahin.mynewsapp.SERVER_PORT
import com.umutcansahin.mynewsapp.TOP_HEADLINES_TEST
import com.umutcansahin.mynewsapp.newsResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var newsApi: NewsApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(SERVER_PORT)

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsApi = retrofit.create(NewsApi::class.java)
    }

    @Test
    fun `when all news api response is not null`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(newsResponse)

        mockWebServer.enqueue(mockResponse)

        val news = newsApi.getAllNews(COUNTRY_CODE)
        assertThat(news).isNotNull()
    }

    @Test
    fun `requestPath when all news requested is the same request`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(newsResponse)

        mockWebServer.enqueue(mockResponse)

        newsApi.getAllNews(COUNTRY_CODE)
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo(TOP_HEADLINES_TEST)
    }

    @Test
    fun `when the all news first element the same with the requested`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(newsResponse)

        mockWebServer.enqueue(mockResponse)
        val response = newsApi.getAllNews(COUNTRY_CODE)
        val firstItem = response.article?.first()
        assertThat(firstItem?.author).isEqualTo("Laura Trujillo")
        assertThat(firstItem?.source?.id).isEqualTo("usa-today")
    }

    @Test
    fun `when search news api response is not null`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(newsResponse)

        mockWebServer.enqueue(mockResponse)

        val news = newsApi.getNewsBySearch(SEARCH_Q, COUNTRY_CODE)
        assertThat(news).isNotNull()
    }

    @Test
    fun `requestPath when search news requested is the same request`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(newsResponse)

        mockWebServer.enqueue(mockResponse)

        newsApi.getNewsBySearch(SEARCH_Q, COUNTRY_CODE)
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo(EVERYTHING_TEST)
    }

    @Test
    fun `when the search news first element the same with the requested`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(newsResponse)

        mockWebServer.enqueue(mockResponse)
        val response = newsApi.getAllNews(COUNTRY_CODE)
        val firstItem = response.article?.first()
        assertThat(firstItem?.publishedAt).isEqualTo("2023-08-19T15:49:32Z")
        assertThat(firstItem?.source?.name).isEqualTo("USA Today")
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}