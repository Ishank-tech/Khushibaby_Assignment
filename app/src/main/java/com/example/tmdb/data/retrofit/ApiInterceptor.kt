package com.example.tmdb.data.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(
    private val apiKey: String,
    private val languageCode: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val url = originalUrl.newBuilder()
            .addQueryParameter(PARAM_API_KEY, apiKey)
            .addQueryParameter(PARAM_LANGUAGE, languageCode)
            .build()

        val request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

    companion object {

        private const val PARAM_API_KEY = "api_key"
        private const val PARAM_LANGUAGE = "language"

    }

}