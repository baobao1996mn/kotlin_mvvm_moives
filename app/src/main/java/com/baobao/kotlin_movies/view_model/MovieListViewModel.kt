package com.baobao.kotlin_movies.view_model

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.baobao.kotlin_movies.R
import com.baobao.kotlin_movies._base.BaseViewModel
import com.baobao.kotlin_movies.api.MovieApi
import com.baobao.kotlin_movies.injection.model.MovieList
import com.baobao.kotlin_movies.view.movie_list.MovieListAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieListViewModel : BaseViewModel() {
    @Inject
    lateinit var movieApi: MovieApi
    val movieListAdapter: MovieListAdapter = MovieListAdapter()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    private lateinit var subscription: Disposable

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    init {
        loadingVisibility.value = View.VISIBLE
        loadMovies()
    }

    private fun loadMovies() {
        subscription = Observable.fromCallable { }
            .concatMap {
                movieApi.getMovies().concatMap { apiMovieList ->
                    Observable.just(apiMovieList)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onGetMovieListStarted() }
            .doOnTerminate { onGetMovieListCompleted() }
            .doOnError { e -> Log.i(this.javaClass.name, e.message) }
            .subscribe(
                { result -> onGetMovieListSuccess(result) },
                { onGetMovieListFail() }
            )
    }

    private fun onGetMovieListStarted() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun onGetMovieListCompleted() {
        loadingVisibility.value = View.GONE
    }

    private fun onGetMovieListSuccess(movieList: MovieList) {
        movieListAdapter.setMovieList(movieList)
    }

    private fun onGetMovieListFail() {
        Log.d(this.javaClass.simpleName,"Loading fail")
    }
}