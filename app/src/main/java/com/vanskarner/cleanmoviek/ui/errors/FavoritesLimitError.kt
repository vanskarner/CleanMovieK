package com.vanskarner.cleanmoviek.ui.errors

import androidx.core.content.ContextCompat
import com.vanskarner.cleanmoviek.R
import com.vanskarner.cleanmoviek.databinding.PremiumErrorDialogBinding

class FavoritesLimitError(private val errorDialog: ErrorDialog) :
    ErrorView<PremiumErrorDialogBinding> {


    override fun setupView(action: Runnable): PremiumErrorDialogBinding {
        val binding = PremiumErrorDialogBinding
            .inflate(errorDialog.getLayoutInflater())
        val color = ContextCompat.getColor(binding.root.context, R.color.gold)
        binding.localSaveServiceView.setBackgroundColor(color)
        binding.acceptBtn.setOnClickListener { action.run() }
        return binding
    }


}