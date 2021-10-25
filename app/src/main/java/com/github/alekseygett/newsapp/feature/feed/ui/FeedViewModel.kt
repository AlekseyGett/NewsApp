package com.github.alekseygett.newsapp.feature.feed.ui

import androidx.lifecycle.viewModelScope
import com.github.alekseygett.newsapp.base.BaseViewModel
import com.github.alekseygett.newsapp.base.Event
import com.github.alekseygett.newsapp.feature.feed.domain.FeedInteractor
import kotlinx.coroutines.launch

class FeedViewModel(private val interactor: FeedInteractor) : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState = ViewState(emptyList(), false)

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnNewsRequest -> {
                processDataEvent(DataEvent.OnLoadData)

                viewModelScope.launch {
                    interactor.getArticles().fold(
                        onSuccess = { articles ->
                            processDataEvent(DataEvent.OnSuccessNewsRequest(articles))
                        },
                        onError = { error ->
                            val errorMessage = error.localizedMessage ?: ""
                            processDataEvent(DataEvent.OnFailureNewsRequest(errorMessage))
                        }
                    )
                }
            }
            is UiEvent.OnBookmarkButtonClick -> {
                viewModelScope.launch {
                    val article = event.article

                    if (article.isBookmarked) {
                        interactor.removeBookmark(article)
                    } else {
                        interactor.addBookmark(article)
                    }

                    processUiEvent(UiEvent.OnNewsRequest)
                }
            }
            is DataEvent.OnLoadData -> {
                return previousState.copy(isLoading = true)
            }
            is DataEvent.OnSuccessNewsRequest -> {
                return previousState.copy(articles = event.articles, isLoading = false)
            }
            is DataEvent.OnFailureNewsRequest -> {

            }
        }

        return null
    }

}