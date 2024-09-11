package com.vanskarner.movie.persistence.remote

import com.vanskarner.movie.MovieBasicDS
import com.vanskarner.movie.MovieDetailDS
import com.vanskarner.movie.MovieRemoteError
import com.vanskarner.movie.businesslogic.MovieRemoteRepository
import java.io.IOException

internal class RetrofitMovieRepository(
    private val apiKey: String,
    private val apiClient: MovieApiClient
) : MovieRemoteRepository {

    override suspend fun getMovies(page: Int): Result<List<MovieBasicDS>> {
        try {
            val response = apiClient.getUpcomingMovies(page, apiKey)
            return when (response.isSuccessful) {
                true -> {
                    val result = response.body() ?: MoviesResultDTO()
                    val list = result.results.map { item -> item.toMovieBasic() }
                    Result.success(list)
                }

                false -> Result.failure(filterError(response.code()))
            }
        } catch (e: IOException) {
            return Result.failure(MovieRemoteError.NoInternet)
        }
    }

    override suspend fun getMovie(movieId: Int): Result<MovieDetailDS> {
        try {
            val response = apiClient.getMovieDetail(movieId, apiKey)
            return when (response.isSuccessful) {
                true -> {
                    val result = response.body() ?: MovieDTO()
                    Result.success(result.toData())
                }

                false -> Result.failure(filterError(response.code()))
            }
        } catch (e: IOException) {
            return Result.failure(MovieRemoteError.NoInternet)
        }
    }

    private fun filterError(code: Int): MovieRemoteError {
        return when (code) {
            401 -> MovieRemoteError.Unauthorised
            404 -> MovieRemoteError.NotFound
            503 -> MovieRemoteError.ServiceUnavailable
            else -> MovieRemoteError.Server
        }
    }

}