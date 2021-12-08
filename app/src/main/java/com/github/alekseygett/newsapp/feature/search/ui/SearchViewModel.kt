package com.github.alekseygett.newsapp.feature.search.ui

import androidx.lifecycle.viewModelScope
import com.github.alekseygett.newsapp.base.BaseViewModel
import com.github.alekseygett.newsapp.base.Event
import com.github.alekseygett.newsapp.feature.search.domain.SearchInteractor
import kotlinx.coroutines.launch

class SearchViewModel(private val interactor: SearchInteractor) : BaseViewModel<ViewState>() {

    override fun initialViewState() = ViewState(
        isLoading = false,
        articles = emptyList(),
        errorMessage = null,
        isSearchFieldVisible = false,
        query = ""
    )

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnNewsRequest -> {
                processDataEvent(DataEvent.OnLoadData)

                viewModelScope.launch {
                    interactor.getArticles(event.query).fold(
                        onSuccess = { articles ->
                            processDataEvent(DataEvent.OnSuccessNewsRequest(articles))
                        },
                        onError = { error ->
                            val errorMessage = error.localizedMessage ?: ""
                            processDataEvent(DataEvent.OnFailureNewsRequest(errorMessage))
                        }
                    )
                }

                return previousState.copy(query = event.query)
            }
            is UiEvent.OnErrorMessageShow -> {
                return previousState.copy(errorMessage = null)
            }
            is UiEvent.OnSearchButtonClick -> {
                return previousState.copy(isSearchFieldVisible = !previousState.isSearchFieldVisible)
            }
            is UiEvent.OnBookmarkButtonClick -> {
                viewModelScope.launch {
                    val article = event.article

                    if (article.isBookmarked) {
                        interactor.removeBookmark(article)
                    } else {
                        interactor.addBookmark(article)
                    }

                    processUiEvent(UiEvent.OnNewsRequest(previousState.query))
                }
            }
            is DataEvent.OnLoadData -> {
                return previousState.copy(isLoading = true)
            }
            is DataEvent.OnSuccessNewsRequest -> {
                return previousState.copy(articles = event.articles, isLoading = false)
            }
            is DataEvent.OnFailureNewsRequest -> {
                return previousState.copy(errorMessage = event.errorMessage)
            }
        }

        return null
    }

}