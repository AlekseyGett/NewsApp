package com.github.alekseygett.newsapp.feature.search.ui

import com.github.alekseygett.newsapp.base.Event
import com.github.alekseygett.newsapp.domain.model.Article

data class ViewState(
    val isLoading: Boolean,
    val articles: List<Article>,
    val errorMessage: String?,
    val isSearchFieldVisible: Boolean,
    val query: String
)

sealed class UiEvent: Event {
    object OnErrorMessageShow : UiEvent()
    object OnSearchButtonClick : UiEvent()
    data class OnNewsRequest(val query: String) : UiEvent()
    data class OnBookmarkButtonClick(val article: Article) : UiEvent()
}

sealed class DataEvent: Event {
    object OnLoadData : DataEvent()
    data class OnSuccessNewsRequest(val articles: List<Article>) : DataEvent()
    data class OnFailureNewsRequest(val errorMessage: String) : DataEvent()
}