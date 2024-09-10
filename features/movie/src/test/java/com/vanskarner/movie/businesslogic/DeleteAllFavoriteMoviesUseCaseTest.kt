package com.vanskarner.movie.businesslogic

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.Mockito.`when`
import org.junit.Assert.*

class DeleteAllFavoriteMoviesUseCaseTest {
    private lateinit var useCase: DeleteAllFavoriteMoviesUseCase
    private val localRepository: MovieLocalRepository = mock()

    @Before
    fun setup() {
        useCase = DeleteAllFavoriteMoviesUseCase(localRepository)
    }

    @Test
    fun execute_returnNumberItemsDeleted() = runTest {
        val expectedDeletedItems = 12
        `when`(localRepository.deleteAllMovies()).thenReturn(Result.success(expectedDeletedItems))
        val actualDeletedItems = useCase.execute().getOrThrow()

        assertEquals(expectedDeletedItems, actualDeletedItems)
    }
}