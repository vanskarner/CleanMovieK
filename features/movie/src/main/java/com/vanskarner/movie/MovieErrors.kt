package com.vanskarner.movie

import java.io.IOException

sealed class MovieError : Exception() {

    data object FavoriteLimitError : MovieError() {
        private fun readResolve(): Any = FavoriteLimitError
    }

}

sealed class MovieRemoteError : IOException() {

    data object Unauthorised : MovieRemoteError() {
        private fun readResolve(): Any = Unauthorised
    }

    data object NotFound : MovieRemoteError() {
        private fun readResolve(): Any = NotFound
    }

    data object Server : MovieRemoteError() {
        private fun readResolve(): Any = Server
    }

    data object ServiceUnavailable : MovieRemoteError() {
        private fun readResolve(): Any = ServiceUnavailable
    }

    data object NoInternet : MovieRemoteError() {
        private fun readResolve(): Any = NoInternet
    }
}