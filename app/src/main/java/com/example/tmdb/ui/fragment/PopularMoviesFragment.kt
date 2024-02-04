package com.example.tmdb.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentPopularMoviesBinding
import com.example.tmdb.ui.adapter.MovieAdapter
import com.example.tmdb.ui.viewmodels.PopularMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment : Fragment(R.layout.fragment_popular_movies) {

    private val viewModel: PopularMoviesViewModel by viewModels()

    private val movieAdapter by lazy { MovieAdapter() }

    private var bindingFiled: FragmentPopularMoviesBinding? = null

    private val binding: FragmentPopularMoviesBinding
        get() = bindingFiled ?: throw IllegalAccessException("Cannot access binding!")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.also {
            bindingFiled = FragmentPopularMoviesBinding.bind(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        lifecycleScope.launch {
            viewModel.movieListPagingDataFlow.collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingFiled = null
    }

    private fun setupRecyclerView() {
        binding.movieRecyclerView.apply {
            adapter = movieAdapter
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        }
    }

}