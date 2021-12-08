package com.github.alekseygett.newsapp.di

import androidx.room.Room
import com.github.alekseygett.newsapp.AppDatabase
import com.github.alekseygett.newsapp.data.ArticlesRepository
import com.github.alekseygett.newsapp.data.ArticlesRepositoryImpl
import com.github.alekseygett.newsapp.data.BookmarksRepository
import com.github.alekseygett.newsapp.data.BookmarksRepositoryImpl
import com.github.alekseygett.newsapp.data.api.NewsApi
import com.github.alekseygett.newsapp.data.api.NewsRemoteSource
import com.github.alekseygett.newsapp.data.local.ArticleDao
import com.github.alekseygett.newsapp.utils.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org/"
const val DATABASE = "NewsAppDatabase"

val appModule = module {
    single<OkHttpClient> {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    single<NewsApi> {
        val retrofit = get<Retrofit>()
        retrofit.create(NewsApi::class.java)
    }

    single<NewsRemoteSource> {
        NewsRemoteSource(get<NewsApi>())
    }

    single<ArticlesRepository> {
        ArticlesRepositoryImpl(get<NewsRemoteSource>())
    }

    single<AppDatabase> {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    single<ArticleDao> {
        get<AppDatabase>().articleDao()
    }

    single<BookmarksRepository> {
        BookmarksRepositoryImpl(get<ArticleDao>())
    }
}