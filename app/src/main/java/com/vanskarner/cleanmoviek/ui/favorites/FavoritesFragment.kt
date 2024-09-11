package com.vanskarner.cleanmoviek.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.vanskarner.cleanmoviek.R
import com.vanskarner.cleanmoviek.databinding.FavoriteDetailDialogBinding
import com.vanskarner.cleanmoviek.databinding.FavoritesFragmentBinding
import com.vanskarner.cleanmoviek.ui.BaseBindingFragment
import com.vanskarner.cleanmoviek.ui.MovieBasicModel
import com.vanskarner.cleanmoviek.ui.MovieDetailModel
import com.vanskarner.cleanmoviek.ui.errors.ErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class FavoritesFragment : BaseBindingFragment<FavoritesFragmentBinding>() {

    private val viewModel: FavoritesViewModel by viewModels()

    @Inject
    lateinit var favoritesAdapter: FavoritesAdapter

    @Inject
    lateinit var errorDialog: ErrorDialog

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FavoritesFragmentBinding = FavoritesFragmentBinding.inflate(layoutInflater)

    override fun setupView() {
        binding.favoritesRecycler.adapter = favoritesAdapter
        favoritesAdapter.onClick = { viewModel.favoriteDetail(it.id) }
        binding.favoritesToolbar.setOnMenuItemClickListener { item: MenuItem ->
            if (item.itemId == R.id.deleteMenuItem) showDeleteDialog { viewModel.deleteAllFavorites() }
            false
        }

    }

    override fun setupViewModel() {
        viewModel.favoritesList()
        viewModel.list.observe(viewLifecycleOwner) { showFavorites(it) }
        viewModel.item.observe(viewLifecycleOwner) { showDetail(it) }
        viewModel.visibilityDeletedItems.observe(viewLifecycleOwner) {
            binding.noFavoritesView.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog.setError(it) { errorDialog.dismiss() }
            errorDialog.show(childFragmentManager)
        }
    }

    private fun showDeleteDialog(onAccept: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.msg_delete_favorites)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
                onAccept.invoke()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun showFavorites(list: List<MovieBasicModel>) = favoritesAdapter.updateList(list)

    private fun showDetail(item: MovieDetailModel) {
        val bindingDetail = FavoriteDetailDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingDetail.root)
        bindingDetail.movieDetail = item
        bindingDetail.lifecycleOwner = viewLifecycleOwner
        builder.create().show()
    }

}