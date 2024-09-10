package com.vanskarner.movie.businesslogic

import com.vanskarner.movie.MovieBasicDS
import com.vanskarner.movie.MoviesFilterDS
import org.junit.Test
import org.junit.Assert.*

class FilterUpcomingMoviesUseCaseTest {
    private val unmodifiableList: List<MovieBasicDS> by lazy {
        listOf(
            MovieBasicDS(1, "Movie One", "any"),
            MovieBasicDS(2, "Movie Two", "any"),
            MovieBasicDS(3, "Movie Three", "any")
        )
    }
    private val useCase = FilterUpcomingMoviesUseCase()

    @Test
    fun execute_exactSearch_oneMatch() {
        val expectedMatches = 1
        val filterDS = MoviesFilterDS(unmodifiableList, "Movie One")
        val actualMatches = useCase.execute(filterDS).getOrThrow().filterList.size

        assertEquals(expectedMatches, actualMatches)
    }

    @Test
    fun execute_impreciseSearch_multipleMatches() {
        val expectedMatches = 3
        val filterDS = MoviesFilterDS(unmodifiableList, "Movie")
        val actualMatches = useCase.execute(filterDS).getOrThrow().filterList.size

        assertEquals(expectedMatches, actualMatches)
    }

    @Test
    fun execute_wrongSearch_noMatch() {
        val expectedMatches = 0
        val filterDS = MoviesFilterDS(unmodifiableList, "Nothing")
        val actualMatches = useCase.execute(filterDS).getOrThrow().filterList.size

        assertEquals(expectedMatches, actualMatches)
    }

}