package com.vanskarner.movie.businesslogic

import com.vanskarner.movie.MovieDetailDS
import com.vanskarner.movie.MovieError
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.junit.Assert.*

class ToggleMovieFavoriteUseCaseTest {
    private lateinit var useCase: ToggleMovieFavoriteUseCase
    private val localRepository: MovieLocalRepository = mock()
    private val checkFavoriteMovieUseCase = CheckFavoriteMovieUseCase(localRepository)

    @Before
    fun setup() {
        useCase = ToggleMovieFavoriteUseCase(localRepository, checkFavoriteMovieUseCase)
    }

    @Test
    fun execute_withUnregisteredItem_savedItem() = runTest {
        val unregisteredItem = createItem()
        `when`(localRepository.checkMovie(unregisteredItem.id)).thenReturn(Result.success(false))
        `when`(localRepository.getNumberMovies()).thenReturn(Result.success(1))
        `when`(localRepository.saveMovie(unregisteredItem)).thenReturn(Result.success(Unit))
        val favorite = useCase.execute(unregisteredItem).getOrThrow()

        assertTrue(favorite)
    }

    @Test
    fun execute_withRegisteredItem_deletedItem() = runTest {
        val registeredItem = createItem()
        `when`(localRepository.checkMovie(registeredItem.id)).thenReturn(Result.success(true))
        `when`(localRepository.deleteMovie(registeredItem.id)).thenReturn(Result.success(Unit))
        val notFavorite = useCase.execute(registeredItem).getOrThrow()

        assertFalse(notFavorite)
    }

    @Test(expected = MovieError.FavoriteLimitError::class)
    fun execute_withUnregisteredItemAndExceededCapacity_throwException() = runTest {
        val exceededAmount = 5
        val unregisteredItem = createItem()
        `when`(localRepository.checkMovie(unregisteredItem.id)).thenReturn(Result.success(false))
        `when`(localRepository.getNumberMovies()).thenReturn(Result.success(exceededAmount))
        `when`(localRepository.saveMovie(unregisteredItem)).thenReturn(Result.success(Unit))
        useCase.execute(unregisteredItem).getOrThrow()
    }

    private fun createItem() =
        MovieDetailDS(12, "Any Movie", "any", "any", 12, 12f, "any", "any", false)

}