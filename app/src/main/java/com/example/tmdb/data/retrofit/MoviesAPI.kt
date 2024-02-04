package com.example.tmdb.data.retrofit

import com.example.tmdb.data.models.MovieDetailResponse
import com.example.tmdb.data.models.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesAPI {
    @GET("movie/popular")
    suspend fun getMovieList(@Query("page") page: Int): PopularMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") movieId: Int): MovieDetailResponse
}