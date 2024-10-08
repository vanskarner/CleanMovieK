package com.vanskarner.cleanmoviek.ui.errors

import androidx.databinding.ViewDataBinding
import javax.inject.Provider

internal class ViewErrorFilter(
    private val defaultError: UnknownError,
    private val errorMap: Map<Class<*>, @JvmSuppressWildcards Provider<ErrorView<*>>>
) :ErrorFilter{

    override fun filter(throwable: Throwable): ErrorView<out ViewDataBinding> {
        val providerError = errorMap[throwable.javaClass]
        return providerError?.get() ?: defaultError
    }

}