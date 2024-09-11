package com.vanskarner.cleanmoviek.ui.upcomingDetail

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.vanskarner.cleanmoviek.R
import com.vanskarner.cleanmoviek.databinding.UpcomingDetailFragmentBinding
import com.vanskarner.cleanmoviek.ui.BaseBindingFragment
import com.vanskarner.cleanmoviek.ui.errors.ErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class UpcomingDetailFragment : BaseBindingFragment<UpcomingDetailFragmentBinding>() {

    private val viewModel: UpcomingDetailViewModel by viewModels()
    private val args: UpcomingDetailFragmentArgs by navArgs()

    @Inject
    lateinit var errorDialog: ErrorDialog

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): UpcomingDetailFragmentBinding = UpcomingDetailFragmentBinding.inflate(layoutInflater)

    override fun setupView() {
        val toolbar = binding.upcomingDetailToolbar
        val navController = findNavController(binding.root)
        val barConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        setupWithNavController(toolbar, navController, barConfiguration)
        toolbar.setOnMenuItemClickListener { menuItem: MenuItem ->
            if (menuItem.itemId == R.id.favoriteMenuItem) {
                binding.movieDetail?.let {
                    it.image = toBase64(binding.coverPageImage)
                    it.backgroundImage = toBase64(binding.backgroundImage)
                    viewModel.toggleFavorite(it)
                }
            }
            false
        }
    }

    override fun setupViewModel() {
        viewModel.checkFavorite(args.movieId)
        viewModel.item.observe(viewLifecycleOwner) { binding.movieDetail = it }
        viewModel.markedAsFavorite.observe(viewLifecycleOwner) {
            binding.upcomingDetailToolbar.menu
                .findItem(R.id.favoriteMenuItem)
                .setIcon(if (it) R.drawable.ic_favorite_24 else R.drawable.ic_favorite_border_24)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog.setError(it) {
                errorDialog.dismiss()
                requireActivity().onBackPressed()
            }
            errorDialog.isCancelable = false
            errorDialog.show(childFragmentManager)
        }
    }

    private fun toBase64(imageView: ImageView): String {
        val drawable = imageView.drawable
        var result = ""
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            result = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
        }
        return result
    }

}