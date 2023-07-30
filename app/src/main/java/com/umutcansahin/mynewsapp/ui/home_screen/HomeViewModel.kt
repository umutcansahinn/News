package com.umutcansahin.mynewsapp.ui.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutcansahin.mynewsapp.common.Constants.REFRESH_TIME
import com.umutcansahin.mynewsapp.common.Resource
import com.umutcansahin.mynewsapp.domain.use_case.top_headlines_use_case.GetTopHeadlinesNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopHeadlinesNewsUseCase: GetTopHeadlinesNewsUseCase
) : ViewModel() {


    private val _state = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val state = _state.asStateFlow()

    private var pollingJob: Job? = null

    fun startPolling() {
        if (pollingJob != null) return

        pollingJob = viewModelScope.launch {
            while (true) {
                getTopHeadlinesNewsUseCase("us").collect { resources ->
                    when (resources) {
                        is Resource.Loading -> _state.update { HomeUiState.Loading }
                        is Resource.Error -> _state.update { HomeUiState.Error(resources.errorMessage) }
                        is Resource.Success -> _state.update { HomeUiState.Success(resources.data) }
                    }
                }
                delay(REFRESH_TIME)
            }
        }
    }

    fun stopPolling() {
        pollingJob?.cancel()
        pollingJob = null
    }
}