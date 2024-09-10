package com.vanskarner.movie.businesslogic

import com.vanskarner.movie.MovieBasicDS
import com.vanskarner.movie.MovieDetailDS

interface MovieLocalRepository {
    suspend fun getMovies(): Result<List<MovieBasicDS>>
    suspend fun getMovie(movieId: Int): Result<MovieDetailDS>
    suspend fun deleteMovie(movieId: Int): Result<Unit>
    suspend fun deleteAllMovies(): Result<Int>
    suspend fun getNumberMovies(): Result<Int>
    suspend fun checkMovie(movieId: Int): Result<Boolean>
    suspend fun saveMovie(movieDetail: MovieDetailDS): Result<Unit>
}