package com.example.nostratest.presentation.list_headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nostratest.domain.model.Response
import com.example.nostratest.domain.usecase.GetHeadlinesNews
import com.example.nostratest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getHeadlinesNews: GetHeadlinesNews
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    var uiState: StateFlow<UiState> = _uiState

    private var getNewsJob: Job? = null

    fun getNews(page: Int) {
        getNewsJob?.cancel()
        getNewsJob = viewModelScope.launch {
            getHeadlinesNews(page).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                    is Resource.Success -> {
                        val data = result.data
                        _uiState.value = UiState.Success(data)
                    }

                    is Resource.Error -> {
                        val error = result.message ?: "unknown error"
                        _uiState.value = UiState.Error(error)
                    }
                }

            }.launchIn(this)
        }
    }


    sealed class UiState {
        data class Success(val data: Response?) : UiState()
        data class Error(val message: String) : UiState()
        object Loading : UiState()
        object Empty : UiState()
    }
}