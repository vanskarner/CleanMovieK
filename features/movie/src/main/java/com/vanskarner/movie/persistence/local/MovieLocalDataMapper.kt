package com.vanskarner.movie.persistence.local

import com.vanskarner.movie.MovieBasicDS
import com.vanskarner.movie.MovieDetailDS

internal fun MovieEntity.toData() = MovieDetailDS(
    id, title, encodedImage, encodedBackgroundImage, voteCount, voteAverage, releaseDate, overview
)

internal fun MovieDetailDS.toDto() = MovieEntity(
    id, title, image, backgroundImage, voteCount, voteAverage, releaseDate, overview
)

internal fun MovieEntity.toMovieBasic() = MovieBasicDS(
    id, title, encodedImage
)