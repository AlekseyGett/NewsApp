package com.github.alekseygett.newsapp.utils

import okhttp3.Interceptor
import okhttp3.Response

object AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("X-Api-Key", "d45d7b72b5744a7a89fe9274da139fd1")
            .build()

        return chain.proceed(newRequest)
    }
}