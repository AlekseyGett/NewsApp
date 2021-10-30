package com.github.alekseygett.newsapp.feature.bookmarks.ui

import com.github.alekseygett.newsapp.base.Event
import com.github.alekseygett.newsapp.feature.common.domain.Article

data class ViewState(
    val articles: List<Article>,
    val deletedArticle: Article?
)

sealed class UiEvent : Event {
    object OnCachedArticlesRequest : UiEvent()
    object OnUndoDialogShowed : UiEvent()
    data class OnBookmarkButtonClick(val article: Article) : UiEvent()
    data class OnUndoDeletingButtonClick(val article: Article) : UiEvent()
}

sealed class DataEvent : Event {
    data class OnCachedArticlesRead(val articles: List<Article>) : DataEvent()
    data class OnDeleteBookmark(val article: Article) : DataEvent()
}