package com.example.cleanarchicmoview.presentation.moviefavdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchicmoview.R
import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.domain.use_case.favmovie_detail.FavMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieFavViewModel @Inject constructor(
    private val getFavMovUseCase: FavMovieDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<favMovieDetState>(favMovieDetState.Init)
    fun getViewState() : StateFlow<favMovieDetState> = _state.asStateFlow()

    fun setLoading(islOading: Boolean) {
        _state.value = favMovieDetState.Loading(islOading)
    }

    fun getfavMovieDetail(id: Long) {
        viewModelScope.launch {
            getFavMovUseCase.getFavMovieById(id).onEach {
                when(it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (it.data == null) {
                            _state.value = favMovieDetState.Error( UiText.StringResource(R.string.movieDetailPage_emptyError))
                        } else {
                            _state.value = favMovieDetState.Success(data = it.data)
                        }
                    }
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = favMovieDetState.Error(it.message)
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class favMovieDetState {
        object Init: favMovieDetState()
        data class Loading(val isLoading: Boolean) : favMovieDetState()
        data class Success(val data: Movie) : favMovieDetState()
        data class Error(val error: UiText) : favMovieDetState()
    }
}