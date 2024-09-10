package com.vanskarner.movie

sealed class MovieError : Exception() {

    data object FavoriteLimitError: MovieError() {
        private fun readResolve(): Any = FavoriteLimitError
    }

}