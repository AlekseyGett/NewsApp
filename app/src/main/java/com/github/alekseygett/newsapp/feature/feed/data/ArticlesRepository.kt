package com.github.alekseygett.newsapp.feature.feed.data

import com.github.alekseygett.newsapp.feature.common.domain.Article
import com.github.alekseygett.newsapp.feature.feed.domain.model.Language
import com.github.alekseygett.newsapp.feature.feed.domain.model.SortingOption
import java.util.*

interface ArticlesRepository {
    suspend fun getArticles(
        query: String = "",
        sources: String = "",
        language: Language,
        from: Date,
        sorting: SortingOption = SortingOption.PublishedAt
    ): List<Article>
}