package com.github.alekseygett.newsapp.data

import com.github.alekseygett.newsapp.domain.model.Article

interface BookmarksRepository {
    suspend fun create(article: Article)
    suspend fun read(): List<Article>
    suspend fun update(article: Article)
    suspend fun delete(article: Article)
}