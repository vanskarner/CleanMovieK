package com.vanskarner.movie.businesslogic

class FindFavoriteMovieUseCase(private val localRepository: MovieLocalRepository) {

    suspend fun execute(movieId: Int) = localRepository.getMovie(movieId)

}