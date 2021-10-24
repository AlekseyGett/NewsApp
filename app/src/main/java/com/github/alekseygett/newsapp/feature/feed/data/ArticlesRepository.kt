package com.github.alekseygett.newsapp.feature.feed.data

import com.github.alekseygett.newsapp.feature.common.domain.Article
import com.github.alekseygett.newsapp.feature.feed.domain.Language
import com.github.alekseygett.newsapp.feature.feed.domain.SortingOption
import java.util.*

interface ArticlesRepository {
    suspend fun getArticles(
        query: String = "",
        language: Language,
        from: Date,
        sorting: SortingOption = SortingOption.PublishedAt
    ): List<Article>
}