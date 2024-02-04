package com.example.tmdb.data.models

data class PopularMoviesResponse(
    val page: Int,
    val results: List<PopularMovie>,
    val total_pages: Int,
    val total_results: Int
)