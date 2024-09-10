package com.vanskarner.movie.businesslogic

class DeleteAllFavoriteMoviesUseCase(private val localRepository: MovieLocalRepository) {

    suspend fun execute() = localRepository.deleteAllMovies()

}