package com.umutcansahin.mynewsapp.ui.home_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.umutcansahin.mynewsapp.databinding.FragmentHomeBinding
import com.umutcansahin.mynewsapp.manager.loading.LoadingIndicator
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun observeData() {
        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collect { states ->
                when (states) {
                    is HomeUiState.Loading -> {
                        loadingIndicator.showLoading()
                    }
                    is HomeUiState.Error -> {
                        loadingIndicator.hideLoading()
                    }
                    is HomeUiState.Success -> {
                        loadingIndicator.hideLoading()
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