package com.vanskarner.movie.persistence.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class TestRoomDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}