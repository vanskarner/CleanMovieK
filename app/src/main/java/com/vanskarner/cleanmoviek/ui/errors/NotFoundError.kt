package com.vanskarner.cleanmoviek.ui.errors

import com.vanskarner.cleanmoviek.R
import com.vanskarner.cleanmoviek.databinding.CommonErrorDialogBinding

class NotFoundError(private val errorDialog: ErrorDialog) : ErrorView<CommonErrorDialogBinding> {

    override fun setupView(action: Runnable): CommonErrorDialogBinding {
        val binding = CommonErrorDialogBinding
            .inflate(errorDialog.getLayoutInflater())
        binding.ivError.setImageResource(R.drawable.ic_not_found)
        binding.tvMsgError.setText(R.string.msg_not_found)
        binding.errorButton.setOnClickListener { action.run() }
        return binding
    }

}