package com.example.tmdb.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tmdb.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {
    val movieListPagingDataFlow by lazy {
        movieRepository
            .getPopularMovies()
            .cachedIn(viewModelScope)
    }
}