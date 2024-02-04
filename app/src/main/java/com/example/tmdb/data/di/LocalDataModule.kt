package com.example.tmdb.data.di

import android.content.Context
import androidx.room.Room
import com.example.tmdb.data.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext applicationContext: Context): MovieDatabase {
        val databaseBuilder = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java,
            MovieDatabase.NAME
        )

        return databaseBuilder.build()
    }

}