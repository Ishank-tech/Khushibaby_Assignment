package com.example.tmdb.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.models.MovieDetailResponse
import com.example.tmdb.data.models.PopularMovie
import com.example.tmdb.data.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieDetailViewModel @AssistedInject constructor(
    @Assisted private val popularMovie: PopularMovie,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _popularMovie = MutableLiveData(
        popularMovie.run {
            MovieDetailResponse(
                id = id,
                title = title,
                overview = overview,
                release_date = release_date,
                poster_path = poster_path,
                backdrop_path = null,
                runtime = 0,
                genres = emptyList()
            )
        }
    )

    val movieDetailResponse: LiveData<MovieDetailResponse>
        get() = _popularMovie

    init {
        viewModelScope.launch {
            movieRepository.getMovie(popularMovie.id).onSuccess {
                _popularMovie.postValue(it)
            }
        }
    }

    companion object {

        @AssistedFactory
        interface Factory {
            fun create(movieModel: PopularMovie?): MovieDetailViewModel
        }

    }

}