package com.github.alekseygett.newsapp.feature.feed.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = ArticleEntity.TABLE_NAME)
data class ArticleEntity(
    @PrimaryKey
    @ColumnInfo(name = "url")
    val sourceUrl: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?,
    @ColumnInfo(name = "publishedAt")
    val publishedAt: Date,
    @ColumnInfo(name = "bookmarked")
    val isBookmarked: Boolean
) {
    companion object {
        const val TABLE_NAME = "bookmarks_table"
    }
}