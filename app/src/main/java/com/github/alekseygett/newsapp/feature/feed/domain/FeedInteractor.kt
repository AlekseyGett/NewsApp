package com.github.alekseygett.newsapp.feature.feed.domain

import com.github.alekseygett.newsapp.feature.feed.data.ArticlesRepository
import com.github.alekseygett.newsapp.feature.feed.domain.model.Language
import com.github.alekseygett.newsapp.feature.feed.domain.model.SortingOption
import com.github.alekseygett.newsapp.utils.attempt
import java.util.*

class FeedInteractor(private val repository: ArticlesRepository) {
    suspend fun getArticles() = attempt {
        val hourAgo = Calendar.getInstance().run {
            add(Calendar.HOUR_OF_DAY, -1)
            time
        }

        val sources = "lenta"

        repository.getArticles(
            query = "",
            sources = sources,
            language = Language.Russian,
            from = hourAgo,
            sorting = SortingOption.PublishedAt
        )
    }
}