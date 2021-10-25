package com.github.alekseygett.newsapp.feature.feed.data

import com.github.alekseygett.newsapp.feature.common.domain.Article
import com.github.alekseygett.newsapp.feature.feed.data.api.NewsRemoteSource
import com.github.alekseygett.newsapp.feature.feed.data.model.ArticleModel
import com.github.alekseygett.newsapp.feature.feed.domain.model.Language
import com.github.alekseygett.newsapp.feature.feed.domain.model.SortingOption
import com.github.alekseygett.newsapp.feature.feed.domain.toDomainModel
import com.google.gson.internal.bind.util.ISO8601Utils
import java.util.*

class ArticlesRepositoryImpl(private val dataSource: NewsRemoteSource): ArticlesRepository {
    override suspend fun fetchArticles(
        query: String,
        sources: String,
        language: Language,
        from: Date,
        sorting: SortingOption
    ): List<Article> {
        val lowerTimeBound = ISO8601Utils.format(from, false, TimeZone.getTimeZone("UTC"))

        val response = dataSource.getArticles(
            query = query,
            sources = sources,
            language = language.value,
            from = lowerTimeBound,
            sorting = sorting.value
        )

        if (response.errorCode != null) {
            throw ApiException("${response.errorCode}: ${response.errorMessage}")
        }

        return response.articles?.map(ArticleModel::toDomainModel) ?: emptyList()
    }
}
