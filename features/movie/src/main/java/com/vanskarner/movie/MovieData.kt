package com.vanskarner.movie

data class MovieBasicDS(
    val id: Int,
    val title: String,
    val image: String
)

data class MovieDetailDS(
    val id: Int,
    val title: String,
    val image: String,
    val backgroundImage: String,
    val voteCount: Int,
    val voteAverage: Float,
    val releaseDate: String,
    val overview: String,
    var recommended: Boolean = false,
)

data class MoviesFilterDS(
    val fullList: List<MovieBasicDS>,
    val query: CharSequence,
    var filterList: List<MovieBasicDS> = emptyList()
)