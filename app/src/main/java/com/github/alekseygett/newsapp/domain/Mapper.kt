package com.github.alekseygett.newsapp.feature.feed.domain

import com.github.alekseygett.newsapp.domain.model.Article
import com.github.alekseygett.newsapp.data.model.ArticleEntity
import com.github.alekseygett.newsapp.data.model.ArticleModel
import com.google.gson.internal.bind.util.ISO8601Utils
import java.text.ParsePosition
import java.util.*

fun Article.toEntity() = ArticleEntity(
    sourceUrl = sourceUrl,
    title = title,
    description = description,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    isBookmarked = isBookmarked
)

fun ArticleEntity.toDomainModel() = Article(
    sourceUrl = sourceUrl,
    title = title,
    description = description,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    isBookmarked = isBookmarked
)

fun ArticleModel.toDomainModel() = Article(
    title = title,
    description = description,
    sourceUrl = sourceUrl,
    imageUrl = imageUrl,
    publishedAt = ISO8601Utils.parse(publishedAt, ParsePosition(0)),
    isBookmarked = false
)

private fun Date.toLocalDate(): Date {
    val timeZone = Calendar.getInstance().timeZone
    val offset = timeZone.getOffset(time)
    return Date(time + offset)
}