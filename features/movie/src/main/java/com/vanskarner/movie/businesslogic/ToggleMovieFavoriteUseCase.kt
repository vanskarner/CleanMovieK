package com.vanskarner.movie.businesslogic

import com.vanskarner.movie.MovieDetailDS
import com.vanskarner.movie.MovieError

class ToggleMovieFavoriteUseCase(
    private val localRepository: MovieLocalRepository,
    private val checkFavoriteMovieUseCase: CheckFavoriteMovieUseCase
) {

    companion object {
        const val MAXIMUM_MOVIES_SAVED = 5
    }

    suspend fun execute(movieDetailDS: MovieDetailDS): Result<Boolean> {
        val exists = checkFavoriteMovieUseCase.execute(movieDetailDS.id).getOrElse { false }
        return when (exists) {
            true -> {
                localRepository.deleteMovie(movieDetailDS.id)
                Result.success(false)
            }

            false -> {
                val numberMovies = localRepository.getNumberMovies().getOrElse { 0 }
                if (numberMovies >= MAXIMUM_MOVIES_SAVED) Result.failure(MovieError.FavoriteLimitError)
                else {
                    localRepository.saveMovie(movieDetailDS).getOrDefault(Unit)
                    Result.success(true)
                }
            }
        }
    }

}