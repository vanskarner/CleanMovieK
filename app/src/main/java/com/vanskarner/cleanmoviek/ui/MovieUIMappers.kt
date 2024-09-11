package com.vanskarner.cleanmoviek.ui

import com.vanskarner.movie.MovieBasicDS
import com.vanskarner.movie.MovieDetailDS

internal fun MovieDetailDS.toModel() = MovieDetailModel(
    id, title, image, backgroundImage, voteCount, voteAverage, releaseDate, overview, recommended
)

internal fun MovieBasicDS.toModel() = MovieBasicModel(
    id, title, image
)

internal fun MovieDetailModel.toData() = MovieDetailDS(
    id, title, image, backgroundImage, voteCount, voteAverage, releaseDate, overview, recommended
)