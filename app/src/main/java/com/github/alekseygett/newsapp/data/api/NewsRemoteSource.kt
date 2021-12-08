package com.github.alekseygett.newsapp.data.api

class NewsRemoteSource(private val api: NewsApi) {
    suspend fun getArticles(
        query: String,
        sources: String,
        language: String, // ISO-639-1
        from: String, // ISO 8601 (2021-10-17T11:35:54Z) UTC (+000)
        sorting: String
    ) = api.getArticles(
        query = query,
        sources = sources,
        language = language,
        from = from,
        sorting = sorting
    )
}