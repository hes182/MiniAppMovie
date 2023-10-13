package com.example.cleanarchicmoview.presentation.moviefavorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.domain.use_case.favoritemovie.FavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(
    private val favMovieUseCase: FavoriteMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FavoriteViewState>(FavoriteViewState.Init)
    fun getViewState() : StateFlow<FavoriteViewState> = _state.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _state.value = FavoriteViewState.Loading(isLoading = isLoading)
    }

    fun getFavMovies(page: Int) {
        viewModelScope.launch {
            favMovieUseCase.getFavoriteMovie(page).onEach { results ->
                when(results) {
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = FavoriteViewState.Error(results.message)
                    }
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (results.data == null || results.data.isEmpty()) {
                            _state.value = FavoriteViewState.SuccessWithEmptyData
                        } else {
                            _state.value = FavoriteViewState.Success(results.data)
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class FavoriteViewState {
        object Init : FavoriteViewState()
        data class Loading(val isLoading: Boolean) : FavoriteViewState()
        data class Success(val data: List<Movie>) : FavoriteViewState()
        object SuccessWithEmptyData : FavoriteViewState()
        data class Error(val error: UiText) : FavoriteViewState()
    }
}