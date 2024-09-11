package com.vanskarner.cleanmoviek.ui

data class MovieDetailModel(
    val id: Int = 0,
    val title: String = "",
    var image: String = "",
    var backgroundImage: String = "",
    val voteCount: Int = 0,
    val voteAverage: Float = 0f,
    val releaseDate: String = "",
    val overview: String = "",
    val recommended: Boolean = false
)

data class MovieBasicModel(
    val id: Int = 0,
    val title: String = "",
    var image: String = ""
)