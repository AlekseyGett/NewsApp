package com.github.alekseygett.newsapp.feature.feed.data.api

import com.github.alekseygett.newsapp.feature.feed.data.model.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/everything")
    suspend fun getArticles(
        @Query("q") query: String,
        @Query("language") language: String, // ISO-639-1
        @Query("sources") sources: String,
        @Query("from") from: String, // ISO 8601 (2021-10-17T11:35:54Z) UTC (+000)
        @Query("sortBy") sorting: String
    ): NewsModel
}