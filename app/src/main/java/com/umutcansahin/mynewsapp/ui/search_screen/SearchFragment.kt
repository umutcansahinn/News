package com.umutcansahin.mynewsapp.ui.search_screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.umutcansahin.mynewsapp.common.extensions.gone
import com.umutcansahin.mynewsapp.common.extensions.visible
import com.umutcansahin.mynewsapp.databinding.FragmentSearchBinding
import com.umutcansahin.mynewsapp.domain.model.ArticleUiModel
import com.umutcansahin.mynewsapp.manager.loading_indicator.LoadingIndicator
import com.umutcansahin.mynewsapp.manager.recyclerview_listener.RecyclerviewListener
import com.umutcansahin.mynewsapp.ui.MainActivity
import com.umutcansahin.mynewsapp.ui.base.BaseFragment
import com.umutcansahin.mynewsapp.ui.home_screen.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel by viewModels<SearchViewModel>()

    @Inject
    lateinit var loadingIndicator: LoadingIndicator

    private val adapter by lazy {
        HomeAdapter(::navigateToDetailScreen)
    }

    private val recyclerviewListener by lazy {
        RecyclerviewListener(::onScrollUp, ::onScrollDown)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.searchState.flowWithLifecycle(lifecycle).collect { states ->
                when (states) {
                    is SearchUiState.Loading -> {
                        loadingIndicator.showLoading()
                        with(binding) {
                            homeRecyclerview.gone()
                            errorMassage.root.gone()
                            editTextSearch.visible()
                        }
                    }

                    is SearchUiState.Error -> {
                        loadingIndicator.hideLoading()
                        with(binding) {
                            homeRecyclerview.gone()
                            errorMassage.root.visible()
                            editTextSearch.gone()
                        }
                        Log.d("UMUT_UMUT_ERROR", states.errorMessage)
                    }

                    is SearchUiState.Success -> {
                        loadingIndicator.hideLoading()
                        with(binding) {
                            homeRecyclerview.visible()
                            errorMassage.root.gone()
                            editTextSearch.visible()
                        }
                        adapter.submitList(states.data.article)
                    }
                }
            }
        }
    }

    private fun initView() {
        with(binding) {
            homeRecyclerview.adapter = adapter
            homeRecyclerview.addOnScrollListener(recyclerviewListener)

            editTextSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val firstCondition = s != null
                    val secondCondition = s.toString().length > 1
                    if (firstCondition and secondCondition) {
                        viewModel.getNewsBySearch(s.toString())
                        observeData()
                    } else {
                        adapter.currentList.clear()
                    }
                }
            })
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

    private fun navigateToDetailScreen(model: ArticleUiModel) {
        val action =
            SearchFragmentDirections.actionNavigationSearchScreenToNavigationHomeDetailScreen(model)
        findNavController().navigate(action)
    }

    override fun onPause() {
        super.onPause()
        showActionBar()
        showBottomBar()
    }
}