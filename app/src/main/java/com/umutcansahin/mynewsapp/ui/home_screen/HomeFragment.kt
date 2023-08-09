package com.umutcansahin.mynewsapp.ui.home_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.umutcansahin.mynewsapp.common.extensions.gone
import com.umutcansahin.mynewsapp.common.extensions.visible
import com.umutcansahin.mynewsapp.databinding.FragmentHomeBinding
import com.umutcansahin.mynewsapp.domain.model.ArticleUiModel
import com.umutcansahin.mynewsapp.manager.loading_indicator.LoadingIndicator
import com.umutcansahin.mynewsapp.manager.recyclerview_listener.RecyclerviewListener
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
        HomeAdapter(::navigateToDetailScreen)
    }

    private val recyclerviewListener by lazy {
        RecyclerviewListener(::onScrollUp, ::onScrollDown)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initView()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collect { states ->
                if (states.isLoading) {
                    loadingIndicator.showLoading()
                    with(binding) {
                        homeRecyclerview.gone()
                        errorMassage.root.gone()
                    }
                }
                if (states.isError != null) {
                    loadingIndicator.hideLoading()
                    with(binding) {
                        homeRecyclerview.gone()
                        errorMassage.root.visible()
                    }
                    Log.d("UMUT_UMUT_ERROR", states.isError)
                }

                if (states.isSuccess != null) {
                    loadingIndicator.hideLoading()
                    with(binding) {
                        homeRecyclerview.visible()
                        errorMassage.root.gone()
                    }
                    homeAdapter.submitList(states.isSuccess.article)
                }
            }
        }
    }

    private fun initView() {
        with(binding) {
            homeRecyclerview.adapter = homeAdapter
            homeRecyclerview.addOnScrollListener(recyclerviewListener)
        }
    }

    private fun navigateToDetailScreen(model: ArticleUiModel) {
        val action =
            HomeFragmentDirections.actionNavigationHomeScreenToNavigationHomeDetailScreen(model)
        findNavController().navigate(action)
    }

    private fun onScrollUp() {
        showBottomBar()
        showActionBar()
    }

    private fun onScrollDown() {
        hideActionBar()
        hideBottomBar()
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