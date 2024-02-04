package com.example.tmdb.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.tmdb.data.db.MovieDatabase
import com.example.tmdb.utils.DataNotFetchedError
import com.example.tmdb.data.mediator.MovieRemoteMediator
import com.example.tmdb.data.models.MovieDetailResponse
import com.example.tmdb.data.retrofit.MoviesAPI
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRepository @Inject constructor(
    private val movieRemoteMediatorFactory: MovieRemoteMediator.Companion.Factory,
    private val movieDatabase : MovieDatabase,
    private val moviesAPI: MoviesAPI
) {
    fun getPopularMovies() = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = movieRemoteMediatorFactory.create(20),
        pagingSourceFactory = {movieDatabase.movieDao().getPagingSource()}
    ).flow

    suspend fun getMovie(movieId: Int): Result<MovieDetailResponse> {
        val movieDto = try {
            moviesAPI.getMovie(movieId).also { movieDatabase.detailsDao().insertDetails(it) }
        } catch (exception: Exception) {
            if (exception !is IOException && exception !is HttpException) throw exception
            movieDatabase.detailsDao().getDetails(movieId)
        }

        return when (movieDto) {
            null -> Result.failure(DataNotFetchedError("Unable to fetch movie with id $movieId"))
            else -> Result.success(movieDto)
        }
    }

}