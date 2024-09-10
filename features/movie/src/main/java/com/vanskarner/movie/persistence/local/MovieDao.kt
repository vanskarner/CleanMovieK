package com.vanskarner.movie.persistence.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie_detail WHERE id=:id LIMIT 1")
    fun find(id: Int): MovieEntity?

    @Query("SELECT * FROM movie_detail")
    fun toList(): List<MovieEntity>

    @Query("DELETE FROM movie_detail WHERE id=:id")
    fun deleteItem(id: Int)

    @Query("DELETE FROM movie_detail")
    fun deleteAll(): Int

    @Query("SELECT COUNT(id) FROM movie_detail")
    fun getQuantity(): Int

    @Query("SELECT EXISTS(SELECT * FROM movie_detail WHERE id=:id LIMIT 1)")
    fun checkExistence(id: Int): Boolean

}