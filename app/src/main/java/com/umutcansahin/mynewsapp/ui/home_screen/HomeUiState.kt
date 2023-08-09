package com.umutcansahin.mynewsapp.ui.home_screen

import com.umutcansahin.mynewsapp.domain.model.NewsUiModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: String? = null,
    val isSuccess: NewsUiModel? = null
)