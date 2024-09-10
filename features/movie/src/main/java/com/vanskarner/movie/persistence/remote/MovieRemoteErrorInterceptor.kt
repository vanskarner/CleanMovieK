package com.vanskarner.movie.persistence.remote

import com.vanskarner.movie.MovieRemoteError
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class MovieRemoteErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var codeResponse = -2
        try {
            val response = chain.proceed(request)
            if (response.isSuccessful) return response
            codeResponse = response.code
            throw filterError(codeResponse)
        } catch (ignored: IOException) {
            throw filterError(codeResponse)
        }
    }

    private fun filterError(code: Int): MovieRemoteError {
        return when (code) {
            401 -> MovieRemoteError.Unauthorised
            404 -> MovieRemoteError.NotFound
            503 -> MovieRemoteError.ServiceUnavailable
            -2 -> MovieRemoteError.NoInternet
            else -> MovieRemoteError.Server
        }
    }

}