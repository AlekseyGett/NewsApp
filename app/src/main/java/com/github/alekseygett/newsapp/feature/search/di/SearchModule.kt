package com.github.alekseygett.newsapp.feature.search.di

import com.github.alekseygett.newsapp.data.ArticlesRepository
import com.github.alekseygett.newsapp.data.BookmarksRepository
import com.github.alekseygett.newsapp.feature.search.domain.SearchInteractor
import com.github.alekseygett.newsapp.feature.search.ui.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    single<SearchInteractor> {
        SearchInteractor(get<ArticlesRepository>(), get<BookmarksRepository>())
    }

    viewModel<SearchViewModel> {
        SearchViewModel(get<SearchInteractor>())
    }
}