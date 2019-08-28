package com.baobao.kotlin_movies.view_model

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
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
    val loadMoreVisibility: MutableLiveData<Int> = MutableLiveData()

    private lateinit var subscription: Disposable

    val isLoading: Boolean
        get() = this.loadingVisibility.value == View.VISIBLE || this.loadMoreVisibility.value == View.VISIBLE

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    init {
        loadingVisibility.value = View.VISIBLE
        loadMoreVisibility.value = View.GONE
        loadMovies {}
    }

    fun loadMovies(onCompleted: () -> Unit) {
        subscription = Observable.fromCallable { }
            .concatMap {
                movieApi.getMovies(1).concatMap { apiMovieList ->
                    Observable.just(apiMovieList)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onGetMovieListStarted() }
            .doOnTerminate { onGetMovieListCompleted(); onCompleted(); }
            .doOnError { e -> Log.i(this.javaClass.name, e.message) }
            .subscribe(
                { result -> onGetMovieListSuccess(result) },
                { onGetMovieListFail() }
            )
    }

    fun loadMoreMovies(page: Int) {
        subscription = Observable.fromCallable { }
            .concatMap {
                movieApi.getMovies(page).concatMap { apiMovieList ->
                    Observable.just(apiMovieList)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onLoadMoreMovieListStarted() }
            .doOnTerminate {
                onLoadMoreMovieListCompleted(); Log.d(
                this.javaClass.simpleName,
                "Page: $page"
            )
            }
            .doOnError { e -> Log.i(this.javaClass.name, e.message) }
            .subscribe(
                { result -> onLoadMoreMovieListSuccess(result) },
                { onLoadMoreMovieListFail() }
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
        Log.d(this.javaClass.simpleName, "Loading fail")
    }

    //todo load more
    private fun onLoadMoreMovieListStarted() {
        loadMoreVisibility.value = View.VISIBLE
    }

    private fun onLoadMoreMovieListCompleted() {
        loadMoreVisibility.value = View.GONE
    }

    private fun onLoadMoreMovieListSuccess(movieList: MovieList) {
        movieListAdapter.addMovieList(movieList)
    }

    private fun onLoadMoreMovieListFail() {
        Log.d(this.javaClass.simpleName, "Loading fail")
    }
}