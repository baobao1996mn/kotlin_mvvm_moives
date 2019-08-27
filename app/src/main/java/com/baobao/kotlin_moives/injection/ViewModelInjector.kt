package com.baobao.kotlin_moives.injection

import com.baobao.kotlin_moives.module.NetworkModule
import com.baobao.kotlin_moives.view_model.MovieListViewModel
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