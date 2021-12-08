package com.github.alekseygett.newsapp.feature.feed.di

import com.github.alekseygett.newsapp.data.ArticlesRepository
import com.github.alekseygett.newsapp.data.BookmarksRepository
import com.github.alekseygett.newsapp.feature.feed.domain.FeedInteractor
import com.github.alekseygett.newsapp.feature.feed.ui.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val feedModule = module {
    single<FeedInteractor> {
        FeedInteractor(get<ArticlesRepository>(), get<BookmarksRepository>())
    }

    viewModel<FeedViewModel> {
        FeedViewModel(get<FeedInteractor>())
    }
}