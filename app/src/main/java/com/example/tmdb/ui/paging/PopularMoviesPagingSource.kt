package com.example.tmdb.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.data.models.PopularMovie
import com.example.tmdb.data.retrofit.MoviesAPI
import javax.inject.Inject

class PopularMoviesPagingSource @Inject constructor(private val moviesAPI: MoviesAPI): PagingSource<Int, PopularMovie>(){

    override fun getRefreshKey(state: PagingState<Int, PopularMovie>): Int? {
        return state.anchorPosition?.let{
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.prevKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMovie> {
        return try {
            val position = params.key ?: 1
            val response = moviesAPI.getMovieList(position)
            LoadResult.Page(
                data = response.results,
                prevKey =  if(position == 1) null else position - 1,
                nextKey = if(position == response.total_pages) null else position + 1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}