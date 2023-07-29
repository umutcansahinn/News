package com.umutcansahin.mynewsapp.di

import com.umutcansahin.mynewsapp.common.Constants.BASE_URL
import com.umutcansahin.mynewsapp.common.Constants.TIMEOUT_IN_SECOND
import com.umutcansahin.mynewsapp.data.api.NewsApi
import com.umutcansahin.mynewsapp.data.interceptor.KeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): KeyInterceptor {
        return KeyInterceptor()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        apiKeyInterceptor: KeyInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SECOND, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(
        retrofit: Retrofit
    ): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }
}