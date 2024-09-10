package com.vanskarner.movie

import com.vanskarner.movie.businesslogic.ValidateLoginUseCase
import org.junit.Test

import org.junit.Assert.*

class ValidateLoginUseCaseTest {
    private val useCase = ValidateLoginUseCase()

    @Test
    fun execute_withValidCredentials_OK() {
        val isValid = useCase
            .execute("samples@promart.com", "123456")
            .getOrThrow()

        assertTrue(isValid)
    }

    @Test
    fun execute_withInValidCredentials_Error() {
        val isValid = useCase
            .execute("dina_boluarte@asesina.com", "666")
            .getOrThrow()

        assertFalse(isValid)
    }

}