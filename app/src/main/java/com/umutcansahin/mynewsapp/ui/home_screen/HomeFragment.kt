package com.umutcansahin.mynewsapp.ui.home_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.umutcansahin.mynewsapp.databinding.FragmentHomeBinding
import com.umutcansahin.mynewsapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun observeData() {
        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collect { states ->
                when (states) {
                    is HomeUiState.Loading -> {}
                    is HomeUiState.Error -> {}
                    is HomeUiState.Success -> {
                        Log.d("UMUT_UMUT_total", states.data.totalResults.toString())
                    }
                }
            }
        }
    }

    override fun initView() {}

    override fun onResume() {
        super.onResume()
        viewModel.startPolling()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPolling()
    }
}