package com.example.cleanarchicmoview.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.domain.use_case.home.GetPopularMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetPopularMovieUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<HomeViewState>(HomeViewState.Init)
    fun getViewState(): StateFlow<HomeViewState> = _state.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _state.value = HomeViewState.Loading(isLoading)
    }

    fun getMovies(page: Int) {
        viewModelScope.launch {
            getMoviesUseCase.getPopularMovies(page).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = HomeViewState.Error(result.message)
                    }
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (result.data == null || result.data.isEmpty()) {
                            _state.value = HomeViewState.SuccessWithEmptyData
                        } else {
                            _state.value = HomeViewState.Success(result.data)
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class HomeViewState {
        object Init : HomeViewState()
        data class Loading(val isLoading: Boolean) : HomeViewState()
        data class Success(val data: List<Movie>) : HomeViewState()
        object SuccessWithEmptyData : HomeViewState()
        data class Error(val error: UiText) : HomeViewState()
    }
}