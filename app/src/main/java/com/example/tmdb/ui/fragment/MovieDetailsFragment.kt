package com.example.tmdb.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.tmdb.BuildConfig
import com.example.tmdb.R
import com.example.tmdb.data.models.MovieDetailResponse
import com.example.tmdb.databinding.FragmentMovieDetailsBinding
import com.example.tmdb.ui.adapter.GenreAdapter
import com.example.tmdb.ui.viewmodels.MovieDetailViewModel
import com.example.tmdb.utils.viewModelsFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    @Inject
    lateinit var viewModelFactory: MovieDetailViewModel.Companion.Factory

    private val fragmentArgs: MovieDetailsFragmentArgs by navArgs()

    private val viewModel: MovieDetailViewModel by viewModelsFactory { viewModelFactory.create(fragmentArgs.popularMoviesModel) }

    private var bindingFiled: FragmentMovieDetailsBinding? = null

    private val genreAdapter : GenreAdapter by lazy { GenreAdapter(viewModel.movieDetailResponse.value!!.genres!!) }

    private val binding: FragmentMovieDetailsBinding
        get() = bindingFiled ?: throw IllegalAccessException("Cannot access binding!")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.also {
            bindingFiled = FragmentMovieDetailsBinding.bind(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding.backButton.setOnClickListener{
            findNavController().navigateUp()
        }
        viewModel.movieDetailResponse.observe(viewLifecycleOwner, this::onMovieModelChanged)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingFiled = null
    }

    private fun onMovieModelChanged(movieModel: MovieDetailResponse) {
        binding.apply {
            Glide
                .with(requireContext())
                .load(BuildConfig.IMAGE_BASE_URL_BACKDROP+"${movieModel.backdrop_path}")
                .placeholder(R.drawable.img_backdrop_placeholder)
                .into(movieBackdropImage)

            Glide
                .with(requireContext())
                .load(BuildConfig.IMAGE_BASE_URL_POSTER+"${movieModel.poster_path}")
                .placeholder(R.drawable.img_poster_placeholder)
                .into(moviePosterImage)

            movieInfoText.text = getString(R.string.movie_info_format, movieModel.release_date?.split("-")?.get(0) ?: "", movieModel.runtime)
            movieTitleText.text = movieModel.title
            movieOverviewText.text = movieModel.overview
            genreAdapter.genres = movieModel.genres!!
            genreAdapter.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {
        binding.genre.apply {
            adapter = genreAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

}