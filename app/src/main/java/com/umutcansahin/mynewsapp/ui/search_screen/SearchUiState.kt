package com.umutcansahin.mynewsapp.ui.search_screen

import com.umutcansahin.mynewsapp.domain.model.NewsUiModel

sealed interface SearchUiState {

    object Loading : SearchUiState
    data class Error(val errorMessage: String) : SearchUiState
    data class Success(val data: NewsUiModel) : SearchUiState

}