package com.github.alekseygett.newsapp.feature.feed.data

import com.github.alekseygett.newsapp.feature.common.domain.Article

interface BookmarksRepository {
    suspend fun create(article: Article)
    suspend fun read(): List<Article>
    suspend fun update(article: Article)
    suspend fun delete(article: Article)
}