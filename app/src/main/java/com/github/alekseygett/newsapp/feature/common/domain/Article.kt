package com.github.alekseygett.newsapp.feature.common.domain

import java.util.*

data class Article(
    val title: String,
    val description: String?,
    val sourceUrl: String,
    val imageUrl: String?,
    val publishedAt: Date,
    val isBookmarked: Boolean
)