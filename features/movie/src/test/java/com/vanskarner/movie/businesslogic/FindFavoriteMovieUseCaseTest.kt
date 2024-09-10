package com.vanskarner.movie.businesslogic

import com.vanskarner.movie.MovieDetailDS
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class FindFavoriteMovieUseCaseTest {
    private lateinit var useCase: FindFavoriteMovieUseCase
    private val localRepository: MovieLocalRepository = mock()

    @Before
    fun setup() {
        useCase = FindFavoriteMovieUseCase(localRepository)
    }

    @Test
    fun execute_withValidID_returnItem() = runTest {
        val expectedItem = MovieDetailDS(12, "", "", "", 12, 12f, "", "", false)
        `when`(localRepository.getMovie(expectedItem.id)).thenReturn(Result.success(expectedItem))
        val actualItem = useCase.execute(expectedItem.id).getOrThrow()

        assertEquals(expectedItem.id, actualItem.id)
        assertEquals(expectedItem.title, actualItem.title)
        assertEquals(expectedItem.image, actualItem.image)
        assertEquals(expectedItem.backgroundImage, actualItem.backgroundImage)
        assertEquals(expectedItem.voteCount, actualItem.voteCount)
        assertEquals(expectedItem.voteAverage, actualItem.voteAverage, 0.01f)
        assertEquals(expectedItem.releaseDate, actualItem.releaseDate)
        assertEquals(expectedItem.overview, actualItem.overview)
    }

}