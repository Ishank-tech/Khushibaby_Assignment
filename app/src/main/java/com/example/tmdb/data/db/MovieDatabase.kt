package com.example.tmdb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdb.data.db.MovieDatabase.Companion.VERSION
import com.example.tmdb.data.models.MovieDetailResponse
import com.example.tmdb.data.models.PopularMovieRemoteKey
import com.example.tmdb.data.models.PopularMovie
import com.example.tmdb.utils.GenreConverter

@Database(entities = [PopularMovie::class, MovieDetailResponse::class, PopularMovieRemoteKey ::class], version = VERSION)
@TypeConverters(GenreConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun detailsDao(): DetailsDao

    abstract fun remoteKeyDao(): RemoteKeysDao

    abstract fun movieDao(): PopularMovieDao

    companion object {

        const val VERSION = 1
        const val NAME = "movie-db"

    }

}