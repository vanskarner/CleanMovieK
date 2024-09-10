package com.vanskarner.movie.persistence.remote

import com.google.gson.annotations.SerializedName

internal data class MovieDTO(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("overview")
    var overview: String = "",
    @SerializedName("poster_path")
    var posterPath: String = "",
    @SerializedName("backdrop_path")
    var backdropPath: String = "",
    @SerializedName("release_date")
    var releaseDate: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("vote_count")
    var voteCount: Int = 0,
    @SerializedName("vote_average")
    var voteAverage: Float = 0f
)

internal data class MoviesResultDTO(
    @SerializedName("results")
    var results: List<MovieDTO> = emptyList()
)