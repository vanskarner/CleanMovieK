package com.vanskarner.movie.businesslogic

import com.vanskarner.movie.MovieBasicDS

class ShowFavoriteMoviesUseCase(private val localRepository: MovieLocalRepository) {

    suspend fun execute(): Result<List<MovieBasicDS>> = localRepository.getMovies()

}