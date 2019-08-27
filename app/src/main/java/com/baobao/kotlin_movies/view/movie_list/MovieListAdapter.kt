package com.baobao.kotlin_movies.view.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baobao.kotlin_movies.R
import com.baobao.kotlin_movies.databinding.ItemMovieBinding
import com.baobao.kotlin_movies.injection.model.Movie
import com.baobao.kotlin_movies.injection.model.MovieList
import com.baobao.kotlin_movies.view_model.MovieItemViewModel

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private lateinit var movieList: MutableList<Movie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return if (::movieList.isInitialized) movieList.size else 0
    }

    fun setMovieList(movieList: MovieList) {
        this.movieList = movieList.result.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = MovieItemViewModel()

        fun bind(movie: Movie) {
            viewModel.bind(movie)
            binding.viewModel = viewModel
        }
    }
}