package com.baobao.kotlin_movies.view.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

    fun addMovieList(movieList: MovieList) {
        this.movieList.addAll(movieList.result.toMutableList())
        if (movieList.result.isNotEmpty()) {
            val start: Int = this.movieList.size - movieList.result.size
            notifyItemRangeChanged(start, movieList.result.size)
        }
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

abstract class PaginationScrollListener
    (var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()

    abstract fun isLoading(): Boolean
}

abstract class SwipeRefreshListener : SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        if (!isLoading())
            refreshItems()
        onRefreshCompleted()
    }

    abstract fun refreshItems()

    abstract fun isLoading(): Boolean

    abstract fun onRefreshCompleted()
}