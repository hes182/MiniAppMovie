package com.example.cleanarchicmoview.presentation.searchmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchicmoview.R
import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.domain.use_case.searchmovie.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
   private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<SearchViewState>(SearchViewState.Init)
    fun getViewState() : StateFlow<SearchViewState> = _state.asStateFlow()
    private var searchJob: Job? = null

    fun setLoading(isLoading: Boolean) {
        _state.value = SearchViewState.Loading(isLoading)
    }

    fun searchMovie(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            searchMovieUseCase.searchMovie(query).onEach {
                when(it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = SearchViewState.Error(it.message)
                    }
                    is Resource.Success -> {
                        setLoading(false)
                        if (!it.data.isNullOrEmpty()) {
                            _state.value = SearchViewState.Success(it.data)
                        } else {
                            _state.value = SearchViewState.Error(
                                UiText.StringResource(R.string.searchPage_noMovieText)
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class SearchViewState {
        object Init : SearchViewState()
        data class Loading(val isLoading: Boolean) : SearchViewState()
        data class Success(val data: List<Movie>) : SearchViewState()
        data class Error(val error: UiText) : SearchViewState()
    }
}