package com.vanskarner.movie.persistence.remote

import com.vanskarner.movie.MovieBasicDS
import com.vanskarner.movie.MovieDetailDS

internal fun MovieDTO.toData() = MovieDetailDS(
    id,
    title,
    posterPath,
    backdropPath,
    voteCount,
    voteAverage,
    releaseDate,
    overview
)

internal fun MovieDTO.toMovieBasic() = MovieBasicDS(
    id, title, posterPath
)
