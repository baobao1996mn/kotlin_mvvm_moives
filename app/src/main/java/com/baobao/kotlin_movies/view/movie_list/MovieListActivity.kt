package com.baobao.kotlin_movies.view.movie_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.baobao.kotlin_movies.R
import com.baobao.kotlin_movies.databinding.ActivityMovieListBinding
import com.baobao.kotlin_movies.injection.ViewModelFactory
import com.baobao.kotlin_movies.view_model.MovieListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieListBinding
    private lateinit var viewModel: MovieListViewModel
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        supportActionBar?.title = "Now Playing"

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_movie_list
        )
        binding.movieList.layoutManager =
            GridLayoutManager(this, 2)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(MovieListViewModel::class.java)
        binding.viewModel = viewModel
    }
}
