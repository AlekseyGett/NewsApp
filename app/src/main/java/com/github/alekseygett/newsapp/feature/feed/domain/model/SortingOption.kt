package com.github.alekseygett.newsapp.feature.feed.domain.model

enum class SortingOption(val value: String) {
    PublishedAt("publishedAt"),
    Relevancy("relevancy"),
    Popularity("popularity")
}