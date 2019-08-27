package com.baobao.kotlin_moives.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baobao.kotlin_moives.view_model.MovieListViewModel

class ViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel() as T
        }
        throw IllegalArgumentException("View Model not found")
    }
}