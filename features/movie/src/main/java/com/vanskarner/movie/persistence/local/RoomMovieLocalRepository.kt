package com.vanskarner.movie.persistence.local

import com.vanskarner.movie.MovieBasicDS
import com.vanskarner.movie.MovieDetailDS
import com.vanskarner.movie.businesslogic.MovieLocalRepository

internal class RoomMovieLocalRepository(private val movieDao: MovieDao) : MovieLocalRepository {

    override suspend fun getMovies(): Result<List<MovieBasicDS>> =
        movieDao.toList()
            .map { it.toMovieBasic() }
            .run { Result.success(this) }

    override suspend fun getMovie(movieId: Int): Result<MovieDetailDS> {
        val item = movieDao.find(movieId) ?: MovieEntity()
        return Result.success(item.toData())
    }

    override suspend fun deleteMovie(movieId: Int): Result<Unit> =
        movieDao.deleteItem(movieId)
            .run { Result.success(Unit) }

    override suspend fun deleteAllMovies(): Result<Int> =
        movieDao.deleteAll()
            .run { Result.success(this) }

    override suspend fun getNumberMovies(): Result<Int> =
        movieDao.getQuantity()
            .run { Result.success(this) }

    override suspend fun checkMovie(movieId: Int): Result<Boolean> =
        movieDao.checkExistence(movieId)
            .run { Result.success(this) }

    override suspend fun saveMovie(movieDetail: MovieDetailDS): Result<Unit> =
        movieDao.insert(movieDetail.toDto())
            .run { Result.success(Unit) }

}