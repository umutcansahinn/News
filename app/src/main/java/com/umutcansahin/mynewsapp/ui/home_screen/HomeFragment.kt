package com.umutcansahin.mynewsapp.ui.home_screen

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.umutcansahin.mynewsapp.common.extensions.gone
import com.umutcansahin.mynewsapp.common.extensions.visible
import com.umutcansahin.mynewsapp.databinding.FragmentHomeBinding
import com.umutcansahin.mynewsapp.manager.loading_indicator.LoadingIndicator
import com.umutcansahin.mynewsapp.manager.recyclerview_listener.RecyclerviewListener
import com.umutcansahin.mynewsapp.ui.MainActivity
import com.umutcansahin.mynewsapp.ui.base.BaseFragment
import com.umutcansahin.mynewsapp.ui.home_screen.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var loadingIndicator: LoadingIndicator

    private val homeAdapter by lazy {
        HomeAdapter()
    }

    private val recyclerviewListener by lazy {
        RecyclerviewListener(::onScrollUp, ::onScrollDown)
    }

    override fun observeData() {
        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collect { states ->
                when (states) {
                    is HomeUiState.Loading -> {
                        loadingIndicator.showLoading()
                        with(binding) {
                            homeRecyclerview.gone()
                            errorMassage.root.gone()
                        }
                    }

                    is HomeUiState.Error -> {
                        loadingIndicator.hideLoading()
                        with(binding) {
                            homeRecyclerview.gone()
                            errorMassage.root.visible()
                        }
                        Log.d("UMUT_UMUT_ERROR", states.errorMessage)
                    }

                    is HomeUiState.Success -> {
                        loadingIndicator.hideLoading()
                        with(binding) {
                            homeRecyclerview.visible()
                            errorMassage.root.gone()
                        }
                        homeAdapter.submitList(states.data.article)
                    }
                }
            }
        }
    }

    override fun initView() {
        with(binding) {
            homeRecyclerview.adapter = homeAdapter
            homeRecyclerview.addOnScrollListener(recyclerviewListener)
        }
    }

    private fun onScrollUp() {
        showBottomBar()
        showActionBar()
    }

    private fun onScrollDown() {
        hideActionBar()
        hideBottomBar()
    }

    private fun hideActionBar() {
        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.hideActionBar()
        }
    }

    private fun showActionBar() {
        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.showActionBar()
        }
    }

    private fun hideBottomBar() {
        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.hideBottomNavigation()
        }
    }

    private fun showBottomBar() {
        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.showBottomNavigation()
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.startPolling()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPolling()
        showActionBar()
        showBottomBar()
    }
}