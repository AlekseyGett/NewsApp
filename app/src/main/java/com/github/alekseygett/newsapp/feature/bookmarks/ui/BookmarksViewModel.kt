package com.github.alekseygett.newsapp.feature.bookmarks.ui

import androidx.lifecycle.viewModelScope
import com.github.alekseygett.newsapp.base.BaseViewModel
import com.github.alekseygett.newsapp.base.Event
import com.github.alekseygett.newsapp.feature.bookmarks.domain.BookmarksInteractor
import kotlinx.coroutines.launch

class BookmarksViewModel(private val interactor: BookmarksInteractor) : BaseViewModel<ViewState>() {

    override fun initialViewState() = ViewState(
        articles = emptyList(),
        deletedArticle = null
    )

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnCachedArticlesRequest -> {
                viewModelScope.launch {
                    val articles = interactor.getArticles()
                    processDataEvent(DataEvent.OnCachedArticlesRead(articles))
                }
            }
            is UiEvent.OnUndoDialogShowed -> {
                return previousState.copy(deletedArticle = null)
            }
            is UiEvent.OnBookmarkButtonClick -> {
                viewModelScope.launch {
                    val article = event.article
                    interactor.removeBookmark(article)
                    processDataEvent(DataEvent.OnDeleteBookmark(article))
                }
            }
            is UiEvent.OnUndoDeletingButtonClick -> {
                viewModelScope.launch {
                    interactor.addBookmark(event.article)
                    processUiEvent(UiEvent.OnCachedArticlesRequest)
                }
            }
            is DataEvent.OnCachedArticlesRead -> {
                return previousState.copy(articles = event.articles)
            }
            is DataEvent.OnDeleteBookmark -> {
                processUiEvent(UiEvent.OnCachedArticlesRequest)
                return previousState.copy(deletedArticle = event.article)
            }
        }

        return null
    }

}