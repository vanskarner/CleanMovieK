package com.vanskarner.cleanmoviek.ui.errors

import com.vanskarner.cleanmoviek.R
import com.vanskarner.cleanmoviek.databinding.CommonErrorDialogBinding

class NoInternetError(private val errorDialog: ErrorDialog) : ErrorView<CommonErrorDialogBinding> {

    override fun setupView(action: Runnable): CommonErrorDialogBinding {
        val binding = CommonErrorDialogBinding
            .inflate(errorDialog.layoutInflater)
        binding.ivError.setImageResource(R.drawable.ic_no_internet)
        binding.tvMsgError.setText(R.string.msg_no_internet)
        binding.errorButton.setOnClickListener { action.run() }
        return binding
    }

}