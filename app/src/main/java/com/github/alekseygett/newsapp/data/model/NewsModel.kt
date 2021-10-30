package com.github.alekseygett.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class NewsModel(
    @SerializedName("articles")
    val articles: List<ArticleModel>?,
    @SerializedName("code")
    val errorCode: String?,
    @SerializedName("message")
    val errorMessage: String?
)