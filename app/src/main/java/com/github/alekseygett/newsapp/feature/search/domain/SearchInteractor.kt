package com.github.alekseygett.newsapp.feature.search.domain

import com.github.alekseygett.newsapp.data.ArticlesRepository
import com.github.alekseygett.newsapp.data.BookmarksRepository
import com.github.alekseygett.newsapp.domain.model.Article
import com.github.alekseygett.newsapp.domain.model.Language
import com.github.alekseygett.newsapp.domain.model.SortingOption
import com.github.alekseygett.newsapp.utils.attempt
import java.util.*

class SearchInteractor(
    private val articlesRepository: ArticlesRepository,
    private val bookmarksRepository: BookmarksRepository
) {

    suspend fun getArticles(query: String) = attempt {
        val hourAgo = Calendar.getInstance().run {
            add(Calendar.HOUR_OF_DAY, -1)
            time
        }

        val sources = "lenta"

        val fetchedArticles = articlesRepository.fetchArticles(
            query = query,
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