package com.github.alekseygett.newsapp.feature.feed.data

import com.github.alekseygett.newsapp.feature.common.domain.Article
import com.github.alekseygett.newsapp.feature.feed.data.local.ArticleDao
import com.github.alekseygett.newsapp.feature.feed.data.model.ArticleEntity
import com.github.alekseygett.newsapp.feature.feed.domain.toDomainModel
import com.github.alekseygett.newsapp.feature.feed.domain.toEntity

class BookmarksRepositoryImpl(private val articlesDao: ArticleDao) : BookmarksRepository {

    override suspend fun create(article: Article) {
        articlesDao.create(article.toEntity())
    }

    override suspend fun read(): List<Article> =
        articlesDao.read().map(ArticleEntity::toDomainModel)

    override suspend fun update(article: Article) {
        articlesDao.update(article.toEntity())
    }

    override suspend fun delete(article: Article) {
        articlesDao.delete(article.toEntity())
    }

}