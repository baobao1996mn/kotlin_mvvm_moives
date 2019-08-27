package com.baobao.kotlin_movies.view_model

import androidx.lifecycle.MutableLiveData
import com.baobao.kotlin_movies._base.BaseViewModel
import com.baobao.kotlin_movies.injection.model.Movie

class MovieItemViewModel : BaseViewModel() {
    val movieAverage = MutableLiveData<String>()
    val movieImage = MutableLiveData<String>()

    fun bind(movie: Movie) {
        movieAverage.value = movie.vote_average.toString()
        movieImage.value = "https://image.tmdb.org/t/p/w500" + movie.poster_path
    }
}