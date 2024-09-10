package com.vanskarner.cleanmoviek.ui.errors

import androidx.databinding.ViewDataBinding
import javax.inject.Provider

class ViewErrorFilter(
    private val defaultError: UnknownError,
    private val errorMap: Map<Class<*>, @JvmSuppressWildcards Provider<ErrorView<*>>>
) {

    fun filter(throwable: Throwable): ErrorView<out ViewDataBinding> {
        val providerError = errorMap[throwable.javaClass]
        return providerError?.get() ?:defaultError
    }

}