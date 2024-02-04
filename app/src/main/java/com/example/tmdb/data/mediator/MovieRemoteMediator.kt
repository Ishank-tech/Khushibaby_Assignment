package com.example.tmdb.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.tmdb.data.db.MovieDatabase
import com.example.tmdb.data.models.PopularMovieRemoteKey
import com.example.tmdb.data.models.PopularMovie
import com.example.tmdb.data.retrofit.MoviesAPI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @AssistedInject constructor(
    @Assisted private val pageSize: Int,
    private val moviesAPI: MoviesAPI,
    private val movieDatabase: MovieDatabase
): RemoteMediator<Int, PopularMovie>() {

    private val movieDao = movieDatabase.movieDao()

    private val remoteKeyDao = movieDatabase.remoteKeyDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PopularMovie>): MediatorResult {
        val curPage = when (loadType) {
            LoadType.REFRESH -> INITIAL_PAGE
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = movieDatabase.withTransaction { remoteKeyDao.getRemoteKeys(REMOTE_KEY_ID) }
                if (remoteKey?.nextPage == null) return MediatorResult.Success(endOfPaginationReached = true)
                remoteKey.nextPage
            }
        }
        return try {
            val getMoveListResponse = moviesAPI.getMovieList(curPage)
            val movieList = getMoveListResponse.results
            val endOfPaginationReached = getMoveListResponse.page == getMoveListResponse.total_pages

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH){
                    movieDao.deleteAllMovies()
                    remoteKeyDao.deleteAllRemoteKeys()
                }

                if (movieList.isNotEmpty()) {
                    movieList.forEachIndexed { index, movieSummaryDto ->
                        movieSummaryDto.order = (curPage * pageSize) + index
                    }

                    movieDao.insertAllMovies(movieList)
                }
                remoteKeyDao.insertRemoteKey(PopularMovieRemoteKey(REMOTE_KEY_ID,
                    curPage.plus(1).takeIf { !endOfPaginationReached }))
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    companion object {

        private const val INITIAL_PAGE = 1
        private const val REMOTE_KEY_ID = "movie_summary"

        @AssistedFactory
        interface Factory {

            fun create(pageSize: Int): MovieRemoteMediator

        }

    }
}