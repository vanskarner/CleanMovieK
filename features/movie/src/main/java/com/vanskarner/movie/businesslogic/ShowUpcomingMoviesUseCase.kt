package com.vanskarner.movie.businesslogic

class ShowUpcomingMoviesUseCase(private val remoteRepository: MovieRemoteRepository) {

    suspend fun execute(page: Int) = remoteRepository.getMovies(page)

}