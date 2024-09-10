package com.vanskarner.cleanmoviek.ui.errors

import com.vanskarner.cleanmoviek.R
import com.vanskarner.cleanmoviek.databinding.CommonErrorDialogBinding

class UnauthorisedError(private val errorDialog: ErrorDialog) :
    ErrorView<CommonErrorDialogBinding> {

    override fun setupView(action: Runnable): CommonErrorDialogBinding {
        val binding = CommonErrorDialogBinding
            .inflate(errorDialog.getLayoutInflater())
        binding.ivError.setImageResource(R.drawable.ic_unauthorised)
        binding.tvMsgError.setText(R.string.msg_unauthorized)
        binding.errorButton.setOnClickListener { action.run() }
        return binding
    }

}