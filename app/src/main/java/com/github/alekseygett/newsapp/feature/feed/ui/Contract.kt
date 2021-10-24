package com.github.alekseygett.newsapp.feature.feed.ui

import com.github.alekseygett.newsapp.base.Event
import com.github.alekseygett.newsapp.feature.common.domain.Article

data class ViewState(
    val articles: List<Article>,
    val isLoading: Boolean
)

sealed class UiEvent: Event {
    object OnNewsRequest : UiEvent()
    data class OnBookmarkButtonClick(val article: Article) : UiEvent()
}

sealed class DataEvent: Event {
    object OnLoadData : DataEvent()
    data class OnSuccessNewsRequest(val articles: List<Article>) : DataEvent()
    data class OnFailureNewsRequest(val errorMessage: String) : DataEvent()
}