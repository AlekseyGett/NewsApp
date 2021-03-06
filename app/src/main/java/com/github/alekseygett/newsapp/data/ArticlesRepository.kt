package com.github.alekseygett.newsapp.data

import com.github.alekseygett.newsapp.domain.model.Article
import com.github.alekseygett.newsapp.domain.model.Language
import com.github.alekseygett.newsapp.domain.model.SortingOption
import java.util.*

interface ArticlesRepository {
    suspend fun fetchArticles(
        query: String = "",
        sources: String = "",
        language: Language,
        from: Date,
        sorting: SortingOption = SortingOption.PublishedAt
    ): List<Article>
}