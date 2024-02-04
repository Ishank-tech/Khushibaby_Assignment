package com.example.tmdb.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.data.models.MovieDetailResponse

@Dao
interface DetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(movieDetailResponse: MovieDetailResponse)

    @Query("SELECT * FROM detail where id = :id")
    suspend fun getDetails(id: Int): MovieDetailResponse?
}