package com.example.tmdb.data.di

import android.content.Context
import com.example.tmdb.BuildConfig
import com.example.tmdb.data.retrofit.ApiInterceptor
import com.example.tmdb.data.retrofit.MoviesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    fun provideApiInterceptor(@ApplicationContext context: Context): ApiInterceptor {
        val currentLocale = context.resources.configuration.locale

        return ApiInterceptor(
            BuildConfig.MOVIE_API_KEY,
            currentLocale.toLanguageTag()
        )
    }

    @Provides
    fun provideOkHttpClient(apiInterceptor: ApiInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.MOVIE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MoviesAPI {
        return retrofit.create(MoviesAPI::class.java)
    }

}