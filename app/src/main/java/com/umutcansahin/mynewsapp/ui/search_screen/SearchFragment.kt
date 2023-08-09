package com.umutcansahin.mynewsapp.ui.search_screen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.umutcansahin.mynewsapp.R
import com.umutcansahin.mynewsapp.common.Constants.MINIMUM_SEARCH_LENGTH
import com.umutcansahin.mynewsapp.common.Constants.SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS
import com.umutcansahin.mynewsapp.common.enums.SortBy
import com.umutcansahin.mynewsapp.common.extensions.gone
import com.umutcansahin.mynewsapp.common.extensions.observeTextChanges
import com.umutcansahin.mynewsapp.common.extensions.okWith
import com.umutcansahin.mynewsapp.common.extensions.visible
import com.umutcansahin.mynewsapp.databinding.FragmentSearchBinding
import com.umutcansahin.mynewsapp.domain.model.ArticleUiModel
import com.umutcansahin.mynewsapp.manager.loading_indicator.LoadingIndicator
import com.umutcansahin.mynewsapp.manager.recyclerview_listener.RecyclerviewListener
import com.umutcansahin.mynewsapp.ui.base.BaseFragment
import com.umutcansahin.mynewsapp.ui.home_screen.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        observeData()
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
                            editTextSearch.isClickable = false
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
                            editTextSearch.isClickable = true
                        }
                        adapter.submitList(states.data.article)
                    }

                    is SearchUiState.EmptyState -> {
                        loadingIndicator.hideLoading()
                        with(binding) {
                            homeRecyclerview.visible()
                            errorMassage.root.gone()
                            editTextSearch.visible()
                            editTextSearch.requestFocus()
                            openKeyboard()
                        }
                    }
                }
            }
        }
    }

    private fun openKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.editTextSearch, InputMethodManager.SHOW_IMPLICIT)

    }

    private fun initView() {
        with(binding) {
            homeRecyclerview.adapter = adapter
            homeRecyclerview.addOnScrollListener(recyclerviewListener)
        }
        editTextChangedListener()
        initRadioGroup()
    }

    @OptIn(FlowPreview::class)
    private fun editTextChangedListener() = with(binding) {
        editTextSearch.observeTextChanges()
            .filter { it okWith MINIMUM_SEARCH_LENGTH }
            .debounce(SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS)
            .distinctUntilChanged()
            .onEach {
                if (it.isBlank().not()) {
                    viewModel.searchQ = it
                    viewModel.getNewsBySearch(viewModel.searchQ, viewModel.sorBy)
                }
            }.launchIn(lifecycleScope)
    }

    private fun initRadioGroup() = with(binding) {
        ivFilter.setOnClickListener {
            viewModel.isRadioGroupVisible = viewModel.isRadioGroupVisible.not()
            radioGroup.isVisible = viewModel.isRadioGroupVisible
        }

        when (viewModel.sorBy) {
            SortBy.PUBLISHED_AT.name -> rbPublishedAt.isChecked = true
            SortBy.RELEVANCY.name -> rbRelevancy.isChecked = true
            SortBy.POPULARITY.name -> rbPopularity.isChecked = true
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbPopularity -> {
                    viewModel.sorBy = SortBy.POPULARITY.name
                }

                R.id.rbPublishedAt -> {
                    viewModel.sorBy = SortBy.PUBLISHED_AT.name
                }

                R.id.rbRelevancy -> {
                    viewModel.sorBy = SortBy.RELEVANCY.name
                }
            }
            viewModel.getNewsBySearch(viewModel.searchQ, viewModel.sorBy)
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