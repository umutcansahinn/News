package com.umutcansahin.mynewsapp.ui.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutcansahin.mynewsapp.common.Resource
import com.umutcansahin.mynewsapp.common.enums.SortBy
import com.umutcansahin.mynewsapp.domain.use_case.search_use_case.GetNewsBySearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getNewsBySearchUseCase: GetNewsBySearchUseCase
) : ViewModel() {

    private val _searchState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val searchState = _searchState.asStateFlow()

    var isRadioGroupVisible: Boolean = false
    var sorBy: String = SortBy.PUBLISHED_AT.name
    var searchQ: String = ""

    fun getNewsBySearch(
        q: String = searchQ,
        sortBy: String = sorBy
    ) = viewModelScope.launch {
        getNewsBySearchUseCase(q, sortBy).collect { resources ->
            when (resources) {
                is Resource.Success -> _searchState.update { SearchUiState.Success(resources.data) }
                is Resource.Error -> _searchState.update { SearchUiState.Error(resources.errorMessage) }
                is Resource.Loading -> _searchState.update { SearchUiState.Loading }
            }
        }
    }

}