package com.vanskarner.movie.businesslogic

internal class DeleteAllFavoriteMoviesUseCase(private val localRepository: MovieLocalRepository) {

    suspend fun execute() = localRepository.deleteAllMovies()

}