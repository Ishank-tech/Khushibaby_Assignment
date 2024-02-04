package com.example.tmdb.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("movie")
@Parcelize
data class PopularMovie(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val adult: Boolean?,
    val backdrop_path: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    var order: Int?
):Parcelable