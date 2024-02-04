package com.example.tmdb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.BuildConfig
import com.example.tmdb.R
import com.example.tmdb.data.models.PopularMovie
import com.example.tmdb.databinding.ListItemBinding
import com.example.tmdb.ui.fragment.PopularMoviesFragmentDirections

class MovieAdapter : PagingDataAdapter<PopularMovie, MovieAdapter.MovieViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { item -> holder.bind(item) }
    }

    companion object {

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PopularMovie>() {

            override fun areItemsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean = oldItem == newItem

        }

    }

    class MovieViewHolder(
        private val binding: ListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(popularMovie: PopularMovie) {
            binding.apply {
                Glide
                    .with(itemView.context)
                    .load(BuildConfig.IMAGE_BASE_URL_POSTER+"${popularMovie.poster_path}")
                    .placeholder(R.drawable.img_poster_placeholder)
                    .into(moviePosterImage)

                movieTitleText.text = popularMovie.title
                movieReleaseYearText.text = popularMovie.release_date?.split("-")?.get(0) ?: ""

                root.setOnClickListener {
                    val action = PopularMoviesFragmentDirections.toMovieDetailFragment(
                        popularMovie
                    )
                    root.findNavController().navigate(action)
                }
            }
        }

        companion object {

            fun newInstance(
                parent: ViewGroup,
            ): MovieViewHolder {
                val binding = ListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return MovieViewHolder(binding)
            }

        }

    }

}

