package com.baobao.kotlin_moives.injection.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("vote_average")
    val vote_average: Double,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("poster_path")
    val poster_path: String? = null
)

data class MovieList(
    @SerializedName("results")
    val result: List<Movie> = emptyList()
)