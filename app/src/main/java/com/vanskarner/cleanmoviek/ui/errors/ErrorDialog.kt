package com.vanskarner.cleanmoviek.ui.errors

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class ErrorDialog : DialogFragment() {
    private val tag: String = "ErrorDialog"
    private lateinit var errorView: ErrorView<ViewDataBinding>
    private lateinit var action: Runnable


    fun setError(errorView: ErrorView<*>, action: Runnable) {
        this.errorView = errorView as ErrorView<ViewDataBinding>
        this.action = action
    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, tag)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: ViewDataBinding = errorView.setupView(action)
        binding.lifecycleOwner = this
        return AlertDialog.Builder(layoutInflater.context)
            .setView(binding.root)
            .create()
    }

}