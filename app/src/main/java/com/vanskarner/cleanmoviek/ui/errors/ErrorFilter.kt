package com.vanskarner.cleanmoviek.ui.errors

interface ErrorFilter {

    fun filter(throwable: Throwable): ErrorView<*>

}