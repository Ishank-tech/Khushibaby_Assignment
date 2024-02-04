package com.example.tmdb.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote-keys")
data class PopularMovieRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val nextPage: Int?
)
