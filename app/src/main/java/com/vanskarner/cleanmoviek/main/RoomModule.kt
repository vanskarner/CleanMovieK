package com.vanskarner.cleanmoviek.main

import android.content.Context
import androidx.room.Room
import com.vanskarner.movie.persistence.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideMovieDao(roomDB: AppRoomDB): MovieDao {
        return roomDB.movieDao()
    }

    @Provides
    @Singleton
    fun provideAppRoomDB(@ApplicationContext context: Context): AppRoomDB {
        return Room.databaseBuilder(context, AppRoomDB::class.java, "MyAppDB")
            .fallbackToDestructiveMigration()
            .build();
    }

}