package com.vanskarner.movie.businesslogic

internal class ShowUpcomingMoviesUseCase(private val remoteRepository: MovieRemoteRepository) {

    suspend fun execute(page: Int) = remoteRepository.getMovies(page)

}