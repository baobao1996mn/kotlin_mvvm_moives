package com.baobao.kotlin_moives._base

import androidx.lifecycle.ViewModel
import com.baobao.kotlin_moives.injection.DaggerViewModelInjector
import com.baobao.kotlin_moives.injection.ViewModelInjector
import com.baobao.kotlin_moives.module.NetworkModule
import com.baobao.kotlin_moives.view_model.MovieListViewModel


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