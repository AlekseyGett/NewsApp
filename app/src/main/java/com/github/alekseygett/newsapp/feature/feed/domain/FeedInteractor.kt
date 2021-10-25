package com.github.alekseygett.newsapp.feature.feed.domain

import com.github.alekseygett.newsapp.feature.common.domain.Article
import com.github.alekseygett.newsapp.feature.feed.data.ArticlesRepository
import com.github.alekseygett.newsapp.feature.feed.data.BookmarksRepository
import com.github.alekseygett.newsapp.feature.feed.domain.model.Language
import com.github.alekseygett.newsapp.feature.feed.domain.model.SortingOption
import com.github.alekseygett.newsapp.utils.attempt
import java.util.*

class FeedInteractor(
    private val articlesRepository: ArticlesRepository,
    private val bookmarksRepository: BookmarksRepository
) {

    suspend fun getArticles() = attempt {
        val hourAgo = Calendar.getInstance().run {
            add(Calendar.HOUR_OF_DAY, -1)
            time
        }

        val sources = "lenta"

        val fetchedArticles = articlesRepository.fetchArticles(
            query = "",
            sources = sources,
            language = Language.Russian,
            from = hourAgo,
            sorting = SortingOption.PublishedAt
        )

        val cachedArticles = bookmarksRepository.read()

        fetchedArticles.map { article ->
            val cached = cachedArticles.find { it.sourceUrl == article.sourceUrl }
            cached?.let { article.copy(isBookmarked = it.isBookmarked) } ?: article
        }
    }

    suspend fun addBookmark(article: Article) {
        bookmarksRepository.create(article.copy(isBookmarked = true))
    }

    suspend fun removeBookmark(article: Article) {
        bookmarksRepository.delete(article)
    }

}