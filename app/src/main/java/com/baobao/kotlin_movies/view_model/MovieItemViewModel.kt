package com.baobao.kotlin_movies.view_model

import androidx.lifecycle.MutableLiveData
import com.baobao.kotlin_movies._base.BaseViewModel
import com.baobao.kotlin_movies.injection.model.Movie

class MovieItemViewModel : BaseViewModel() {
    val movieAverage = MutableLiveData<String>()
    val movieTitle = MutableLiveData<String>()

    fun bind(movie: Movie) {
        movieAverage.value = movie.vote_average.toString()
        movieTitle.value = movie.title
    }
}