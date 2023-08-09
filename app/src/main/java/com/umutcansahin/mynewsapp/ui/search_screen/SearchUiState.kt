package com.umutcansahin.mynewsapp.ui.search_screen

import com.umutcansahin.mynewsapp.domain.model.NewsUiModel

data class SearchUiState(
    val isLoading: Boolean = false,
    val isError: String? = null,
    val isSuccess: NewsUiModel? = null,
    val emptyState: Boolean = true
)