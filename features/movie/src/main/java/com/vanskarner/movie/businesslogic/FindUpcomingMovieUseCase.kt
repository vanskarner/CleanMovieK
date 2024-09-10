package com.vanskarner.movie.businesslogic

import com.vanskarner.movie.MovieDetailDS

class FindUpcomingMovieUseCase(private val remoteRepository: MovieRemoteRepository) {

    suspend fun execute(movieId: Int): Result<MovieDetailDS> {
        return remoteRepository.getMovie(movieId)
            .map {
                it.recommended = it.voteCount >= 75 && it.voteAverage >= 7.5
                it
            }
    }

}