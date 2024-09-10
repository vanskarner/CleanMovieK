package com.vanskarner.movie.businesslogic

internal class ValidateLoginUseCase {
    companion object {
        const val EMAIL = "samples@promart.com"
        const val PASSWORD = "123456"
    }

    fun execute(email: String, password: String): Result<Boolean> =
        Result.success(email == EMAIL && password == PASSWORD)

//    private fun isValidEmail(email: String): Boolean =
//        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex().matches(email)

}