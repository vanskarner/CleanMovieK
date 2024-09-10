package com.vanskarner.movie.persistence.remote

import com.vanskarner.movie.MovieBasicDS
import com.vanskarner.movie.MovieDetailDS
import com.vanskarner.movie.businesslogic.MovieRemoteRepository

internal class RetrofitMovieRepository(
    private val apiKey: String,
    private val apiClient: MovieApiClient
) : MovieRemoteRepository {

    override suspend fun getMovies(page: Int): Result<List<MovieBasicDS>> {
        return apiClient.getUpcomingMovies(page, apiKey).body()
            ?.let {
                val data = it.results.map { item -> item.toMovieBasic() }
                Result.success(data)
            } ?: Result.success(emptyList())
    }

    override suspend fun getMovie(movieId: Int): Result<MovieDetailDS> {
        return apiClient.getMovieDetail(movieId, apiKey).body()
            ?.let {
                Result.success(it.toData())
            } ?: Result.success(MovieDTO().toData())
    }

}