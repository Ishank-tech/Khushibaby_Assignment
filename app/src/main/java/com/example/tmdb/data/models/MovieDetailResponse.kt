package com.example.tmdb.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail")
data class MovieDetailResponse(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val backdrop_path: String?,
    val genres: List<Genre>?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val runtime: Int?,
    val title: String?,
)