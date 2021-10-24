package com.github.alekseygett.newsapp.feature.feed.domain

import com.github.alekseygett.newsapp.feature.common.domain.Article
import com.github.alekseygett.newsapp.feature.feed.data.model.ArticleModel
import com.google.gson.internal.bind.util.ISO8601Utils
import java.text.ParsePosition
import java.util.*

fun ArticleModel.toDomainModel(): Article = Article(
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