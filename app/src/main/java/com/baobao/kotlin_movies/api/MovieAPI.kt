package com.baobao.kotlin_movies.api

import com.baobao.kotlin_movies.injection.model.MovieList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/now_playing?language=en-US")
    fun getMovies(): Observable<MovieList>
}