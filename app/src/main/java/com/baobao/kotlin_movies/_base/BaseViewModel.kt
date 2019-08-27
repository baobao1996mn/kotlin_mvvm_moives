package com.baobao.kotlin_movies._base

import androidx.lifecycle.ViewModel
import com.baobao.kotlin_movies.injection.DaggerViewModelInjector
import com.baobao.kotlin_movies.injection.ViewModelInjector
import com.baobao.kotlin_movies.module.NetworkModule
import com.baobao.kotlin_movies.view_model.MovieListViewModel


abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MovieListViewModel -> injector.inject(this)
        }
    }
}