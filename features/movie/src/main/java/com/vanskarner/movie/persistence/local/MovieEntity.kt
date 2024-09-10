package com.vanskarner.movie.persistence.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail")
data class MovieEntity(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "encoded_image")
    val encodedImage: String = "",
    @ColumnInfo(name = "encoded_background_image")
    val encodedBackgroundImage: String = "",
    @ColumnInfo(name = "vote_count")
    val voteCount: Int = 0,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Float = 0f,
    @ColumnInfo(name = "release_date")
    val releaseDate: String = "",
    @ColumnInfo(name = "overview")
    val overview: String = ""
)