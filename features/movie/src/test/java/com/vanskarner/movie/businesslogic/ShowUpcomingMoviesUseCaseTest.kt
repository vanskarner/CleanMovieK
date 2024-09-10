package com.vanskarner.movie.businesslogic

import com.vanskarner.movie.MovieBasicDS
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.junit.Assert.*
import org.mockito.Mockito.`when`

class ShowUpcomingMoviesUseCaseTest {
    private lateinit var useCase: ShowUpcomingMoviesUseCase
    private val remoteRepository: MovieRemoteRepository = mock()

    @Before
    fun setup() {
        useCase = ShowUpcomingMoviesUseCase(remoteRepository)
    }

    @Test
    fun execute_returnList() = runTest {
        val movies = listOf(
            MovieBasicDS(1, "Any Title 1", "any"),
            MovieBasicDS(2, "Any Title 2", "any"),
        )
        val expectedQuantity = movies.size
        `when`(remoteRepository.getMovies(1)).thenReturn(Result.success(movies))
        val actualQuantity = useCase.execute(1).getOrThrow().size

        assertEquals(expectedQuantity, actualQuantity)
    }

}