package com.vanskarner.movie

import com.vanskarner.movie.businesslogic.CheckFavoriteMovieUseCase
import com.vanskarner.movie.businesslogic.DeleteAllFavoriteMoviesUseCase
import com.vanskarner.movie.businesslogic.FilterUpcomingMoviesUseCase
import com.vanskarner.movie.businesslogic.FindFavoriteMovieUseCase
import com.vanskarner.movie.businesslogic.FindUpcomingMovieUseCase
import com.vanskarner.movie.businesslogic.ShowFavoriteMoviesUseCase
import com.vanskarner.movie.businesslogic.ShowUpcomingMoviesUseCase
import com.vanskarner.movie.businesslogic.ToggleMovieFavoriteUseCase
import com.vanskarner.movie.businesslogic.ValidateLoginUseCase
import javax.inject.Provider

internal class DefaultMovieComponent(
    private val checkFavoriteMovieUseCase: Provider<CheckFavoriteMovieUseCase>,
    private val deleteAllFavoriteMoviesUseCase: Provider<DeleteAllFavoriteMoviesUseCase>,
    private val filterUpcomingMoviesUseCase: Provider<FilterUpcomingMoviesUseCase>,
    private val findFavoriteMovieUseCase: Provider<FindFavoriteMovieUseCase>,
    private val findUpcomingMovieUseCase: Provider<FindUpcomingMovieUseCase>,
    private val showFavoriteMoviesUseCase: Provider<ShowFavoriteMoviesUseCase>,
    private val showUpcomingMoviesUseCase: Provider<ShowUpcomingMoviesUseCase>,
    private val toggleMovieFavoriteUseCase: Provider<ToggleMovieFavoriteUseCase>,
    private val validateLoginUseCase: Provider<ValidateLoginUseCase>
) : MovieComponent {

    override fun logIn(email: String, password: String): Result<Boolean> =
        validateLoginUseCase.get()
            .execute(email, password)

    override suspend fun toggleFavorite(movieDetailDS: MovieDetailDS): Result<Boolean> =
        toggleMovieFavoriteUseCase.get()
            .execute(movieDetailDS)

    override suspend fun checkFavorite(movieId: Int): Result<Boolean> =
        checkFavoriteMovieUseCase.get()
            .execute(movieId)

    override suspend fun deleteAllFavorite(): Result<Int> =
        deleteAllFavoriteMoviesUseCase.get()
            .execute()

    override fun filterUpcoming(moviesFilterDS: MoviesFilterDS): Result<MoviesFilterDS> =
        filterUpcomingMoviesUseCase.get()
            .execute(moviesFilterDS)

    override suspend fun findFavorite(movieId: Int): Result<MovieDetailDS> =
        findFavoriteMovieUseCase.get()
            .execute(movieId)

    override suspend fun findUpcoming(movieId: Int): Result<MovieDetailDS> =
        findUpcomingMovieUseCase.get()
            .execute(movieId)

    override suspend fun showFavorite(): Result<List<MovieBasicDS>> =
        showFavoriteMoviesUseCase.get()
            .execute()

    override suspend fun showUpcoming(page: Int): Result<List<MovieBasicDS>> =
        showUpcomingMoviesUseCase.get()
            .execute(page)

}