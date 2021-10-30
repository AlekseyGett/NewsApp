package com.github.alekseygett.newsapp.feature.bookmarks.di

import com.github.alekseygett.newsapp.feature.bookmarks.domain.BookmarksInteractor
import com.github.alekseygett.newsapp.feature.bookmarks.ui.BookmarksViewModel
import com.github.alekseygett.newsapp.data.BookmarksRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookmarksModule = module {
    single<BookmarksInteractor> {
        BookmarksInteractor(get<BookmarksRepository>())
    }

    viewModel<BookmarksViewModel> {
        BookmarksViewModel(get<BookmarksInteractor>())
    }
}