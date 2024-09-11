package com.vanskarner.cleanmoviek.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.vanskarner.cleanmoviek.R
import com.vanskarner.cleanmoviek.databinding.UpcomingFragmentBinding
import com.vanskarner.cleanmoviek.ui.BaseBindingFragment
import com.vanskarner.cleanmoviek.ui.errors.ErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class UpcomingFragment : BaseBindingFragment<UpcomingFragmentBinding>() {

    private val viewModel: UpcomingViewModel by viewModels()

    @Inject
    lateinit var upcomingAdapter: UpcomingAdapter

    @Inject
    lateinit var errorDialog: ErrorDialog

    @Inject
    lateinit var pagination: Pagination

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): UpcomingFragmentBinding = UpcomingFragmentBinding.inflate(layoutInflater)

    override fun setupView() {
        //configNavToolbar
        val controller = Navigation.findNavController(binding.root)
        val barConfiguration = AppBarConfiguration.Builder(controller.graph).build()
        NavigationUI.setupWithNavController(binding.upcomingToolbar, controller, barConfiguration)
        //configSearchView
        val toolbar = binding.upcomingToolbar
        val searchView = toolbar.menu.findItem(R.id.searchMenuItem).actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnSearchClickListener { viewModel.asyncCancel() }
        searchView.setOnCloseListener {
            pagination.enableScroll()
            false
        }
        //
    }

    override fun setupViewModel() {

    }

}