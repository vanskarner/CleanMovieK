package com.vanskarner.movie.businesslogic

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.junit.Assert.*
import org.mockito.Mockito.`when`

class CheckFavoriteMovieUseCaseTest {
    private lateinit var useCase: CheckFavoriteMovieUseCase
    private lateinit var localRepository: MovieLocalRepository

    @Before
    fun setup() {
        localRepository = mock()
        useCase = CheckFavoriteMovieUseCase(localRepository)
    }

    @Test
    fun execute_withValidID_itemExists() = runTest {
        val validId = 12
        `when`(localRepository.checkMovie(validId)).thenReturn(Result.success(true))
        val exists = useCase.execute(validId).getOrThrow()

        assertTrue(exists)
    }

    @Test
    fun execute_withInvalidID_itemNotExists()= runTest {
        val invalidId = 666
        `when`(localRepository.checkMovie(invalidId)).thenReturn(Result.success(false))
        val exists = useCase.execute(invalidId).getOrThrow()

        assertFalse(exists)
    }

}