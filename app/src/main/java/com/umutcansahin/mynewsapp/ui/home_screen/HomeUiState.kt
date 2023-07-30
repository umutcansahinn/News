package com.umutcansahin.mynewsapp.ui.home_screen

import com.umutcansahin.mynewsapp.domain.model.NewsUiModel

sealed interface HomeUiState {

    object Loading : HomeUiState
    data class Error(val errorMessage: String) : HomeUiState
    data class Success(val data: NewsUiModel) : HomeUiState

}