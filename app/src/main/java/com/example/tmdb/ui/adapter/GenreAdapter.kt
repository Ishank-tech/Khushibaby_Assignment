package com.example.tmdb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.data.models.Genre
import com.example.tmdb.databinding.GenreItemBinding

class GenreAdapter(var genres : List<Genre>) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    class GenreViewHolder(private val binding: GenreItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {
            binding.apply {
                genres.text = genre.name
            }
        }

        companion object {

            fun newInstance(
                parent: ViewGroup,
            ): GenreViewHolder {
                val binding = GenreItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return GenreViewHolder(binding)
            }

        }
    }

}