package com.github.alekseygett.newsapp.feature.feed.di

import com.github.alekseygett.newsapp.feature.feed.data.ArticlesRepository
import com.github.alekseygett.newsapp.feature.feed.data.ArticlesRepositoryImpl
import com.github.alekseygett.newsapp.feature.feed.data.api.NewsApi
import com.github.alekseygett.newsapp.feature.feed.data.api.NewsRemoteSource
import com.github.alekseygett.newsapp.utils.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org/"

val feedModule = module {
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
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
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
}