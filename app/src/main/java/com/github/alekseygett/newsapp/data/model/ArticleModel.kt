package com.github.alekseygett.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class ArticleModel(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val sourceUrl: String,
    @SerializedName("urlToImage")
    val imageUrl: String?,
    @SerializedName("publishedAt")
    val publishedAt: String,
)