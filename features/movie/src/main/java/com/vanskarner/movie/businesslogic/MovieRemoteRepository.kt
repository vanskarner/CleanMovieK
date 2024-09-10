package com.vanskarner.movie.businesslogic

import com.vanskarner.movie.MovieBasicDS
import com.vanskarner.movie.MovieDetailDS

internal interface MovieRemoteRepository {
    suspend fun getMovies(page: Int): Result<List<MovieBasicDS>>
    suspend fun getMovie(movieId: Int): Result<MovieDetailDS>
}