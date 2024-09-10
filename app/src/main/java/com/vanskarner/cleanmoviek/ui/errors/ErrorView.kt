package com.vanskarner.cleanmoviek.ui.errors

import androidx.databinding.ViewDataBinding

interface ErrorView<T: ViewDataBinding> {

    fun setupView(action: Runnable): T

}