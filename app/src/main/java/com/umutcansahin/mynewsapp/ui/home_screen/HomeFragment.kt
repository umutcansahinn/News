package com.umutcansahin.mynewsapp.ui.home_screen

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.umutcansahin.mynewsapp.common.extensions.gone
import com.umutcansahin.mynewsapp.common.extensions.visible
import com.umutcansahin.mynewsapp.databinding.FragmentHomeBinding
import com.umutcansahin.mynewsapp.manager.loading_indicator.LoadingIndicator
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
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startPolling()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPolling()
    }
}