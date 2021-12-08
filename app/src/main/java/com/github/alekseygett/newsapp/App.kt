package com.github.alekseygett.newsapp

import android.app.Application
import com.github.alekseygett.newsapp.di.appModule
import com.github.alekseygett.newsapp.feature.bookmarks.di.bookmarksModule
import com.github.alekseygett.newsapp.feature.feed.di.feedModule
import com.github.alekseygett.newsapp.feature.search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)

            modules(appModule, feedModule, searchModule, bookmarksModule)
        }
    }
}