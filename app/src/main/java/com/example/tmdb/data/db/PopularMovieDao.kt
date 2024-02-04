package com.example.tmdb.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.data.models.PopularMovie

@Dao
interface PopularMovieDao {
    @Query("SELECT * FROM movie order by `order` ASC")
    fun getPagingSource(): PagingSource<Int, PopularMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(popularMovies: List<PopularMovie>)

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovies()
}