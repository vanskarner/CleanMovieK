package com.vanskarner.movie.businesslogic

import com.vanskarner.movie.MovieBasicDS
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.junit.Assert.*
import org.mockito.Mockito.`when`

class ShowFavoriteMoviesUseCaseTest {
    private lateinit var useCase: ShowFavoriteMoviesUseCase
    private val localRepository: MovieLocalRepository = mock()

    @Before
    fun setup() {
        useCase = ShowFavoriteMoviesUseCase(localRepository)
    }

    @Test
    fun execute_returnList() = runTest {
        val movies = listOf(
            MovieBasicDS(1, "Any Title 1", "any"),
            MovieBasicDS(2, "Any Title 2", "any"),
        )
        val expectedQuantity = movies.size
        `when`(localRepository.getMovies()).thenReturn(Result.success(movies))
        val actualQuantity = useCase.execute().getOrThrow().size

        assertEquals(expectedQuantity, actualQuantity)
    }
}