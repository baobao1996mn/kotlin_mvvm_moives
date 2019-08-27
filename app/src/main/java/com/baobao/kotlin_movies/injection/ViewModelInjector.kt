package com.baobao.kotlin_movies.injection

import com.baobao.kotlin_movies.module.NetworkModule
import com.baobao.kotlin_movies.view_model.MovieListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    fun inject(movieListViewModel: MovieListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}