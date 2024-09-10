package com.vanskarner.movie.businesslogic

internal class CheckFavoriteMovieUseCase(private val localRepository: MovieLocalRepository) {

    suspend fun execute(movieId: Int) = localRepository.checkMovie(movieId)

}