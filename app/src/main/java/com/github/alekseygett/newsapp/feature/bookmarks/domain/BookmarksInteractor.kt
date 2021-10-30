package com.github.alekseygett.newsapp.feature.bookmarks.domain

import com.github.alekseygett.newsapp.feature.common.domain.Article
import com.github.alekseygett.newsapp.feature.feed.data.BookmarksRepository

class BookmarksInteractor(private val bookmarksRepository: BookmarksRepository) {

    suspend fun getArticles() = bookmarksRepository.read()

    suspend fun removeBookmark(article: Article) {
        return bookmarksRepository.delete(article)
    }

    suspend fun addBookmark(article: Article) {
        bookmarksRepository.create(article.copy(isBookmarked = true))
    }

}