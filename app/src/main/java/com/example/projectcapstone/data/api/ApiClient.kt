package com.example.projectcapstone.data.api

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

data class Article(
    val title: String,
    val urlImage: String,
    val urlWeb: String,
    val scrapedAt: String
)

data class ArticleResponse(
    val data: List<Article>
)

interface ArticleApiService {
    @GET("scraping")
    suspend fun getArticles(): ArticleResponse
}

object ApiClient {
    private const val BASE_URL = "https://backend-articles-745462549748.asia-southeast2.run.app/api/"

    val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
    val service: ArticleApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleApiService::class.java)
    }
}

