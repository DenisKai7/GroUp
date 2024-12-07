package com.example.projectcapstone.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

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

    val service: ArticleApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleApiService::class.java)
    }
}
