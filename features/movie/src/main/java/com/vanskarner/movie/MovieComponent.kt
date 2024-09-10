package com.vanskarner.movie

interface MovieComponent {
    fun logIn(email: String, password: String): Result<Boolean>
    suspend fun toggleFavorite(movieDetailDS: MovieDetailDS): Result<Boolean>
    suspend fun checkFavorite(movieId: Int): Result<Boolean>
    suspend fun deleteAllFavorite(): Result<Int>
    fun filterUpcoming(moviesFilterDS: MoviesFilterDS): Result<MoviesFilterDS>
    suspend fun findFavorite(movieId: Int): Result<MovieDetailDS>
    suspend fun findUpcoming(movieId: Int): Result<MovieDetailDS>
    suspend fun showFavorite(): Result<List<MovieBasicDS>>
    suspend fun showUpcoming(page: Int): Result<List<MovieBasicDS>>
}