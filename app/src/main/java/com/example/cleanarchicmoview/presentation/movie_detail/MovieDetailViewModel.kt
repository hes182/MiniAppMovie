package com.example.cleanarchicmoview.presentation.movie_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchicmoview.R
import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.data.remote.models.MovieDto
import com.example.cleanarchicmoview.domain.mapper.MovieMapper
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.domain.repositories.MovieRepository
import com.example.cleanarchicmoview.domain.use_case.movie_detail.GetMovieDetailUseCase
import com.example.cleanarchicmoview.presentation.moviefavorite.FavoriteMovieViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val reqpository: MovieRepository
): ViewModel() {

    private val _state = MutableStateFlow<MovieDetailViewState>(MovieDetailViewState.Init)
    fun getViewSet() : StateFlow<MovieDetailViewState> = _state.asStateFlow()

    private val _stateFavMovie = MutableStateFlow<MovieFavMovieState>(MovieFavMovieState.Init)
    fun getViewSetFavMovie() : StateFlow<MovieFavMovieState> = _stateFavMovie.asStateFlow()

    fun setLoading(isLoading : Boolean) {
        _state.value = MovieDetailViewState.Loading(isLoading)
    }

    fun getMovie(id: Long) {
        viewModelScope.launch {
            getMovieDetailUseCase.getMovieById(id).onEach {
                when(it) {
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = MovieDetailViewState.Error(it.message)
                    }
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (it.data == null)
                            _state.value = MovieDetailViewState.Error(
                                UiText.StringResource(R.string.movieDetailPage_emptyError)
                            )
                        else
                           _state.value = MovieDetailViewState.Succes(data = it.data)
                        }
                    }
                }.launchIn(this)
            }
        }

    fun insertFavMovie(data: Movie) {
        viewModelScope.launch {
            val movie = MovieDto(
                adult = false,
                backdropPath = "",
                budget = "",
                genres = null,
                homepage = "",
                id = data.id,
                imdbId = "",
                originalLanguage = "",
                originalTitle = data.title,
                overview = data.overview,
                popularity = 0.0,
                posterPath = data.posterPath,
                releaseDate = data.releaseDate,
                revenue = 0,
                runtime = data.runtime,
                status = "",
                tagline = "",
                title = data.title,
                video = false,
                voteAverage = data.voteAverage
            )
            reqpository.saveMovieFavorite(movie)
        }
    }

    fun setLoadingFavMovie(loading: Boolean) {
        _stateFavMovie.value = MovieFavMovieState.LoadingFav(loading)
    }

    fun getMovieFavById(id: Long){
        viewModelScope.launch {
            getMovieDetailUseCase.getFavMovieById(id).onEach {
                when(it) {
                    is Resource.Loading -> setLoadingFavMovie(true)
                    is Resource.Error -> {
                        setLoadingFavMovie(false)
                        _stateFavMovie.value = MovieFavMovieState.ErrorFav(it.message)
                    }
                    is Resource.Success -> {
                        setLoadingFavMovie(false)
                        if (it.data == null) {
                            _stateFavMovie.value = MovieFavMovieState.SuccessFav(false)
                        } else {
                            _stateFavMovie.value = MovieFavMovieState.SuccessFav(true)
                        }
                    }
                }
            }.launchIn(this)
        }
    }


        sealed class MovieDetailViewState {
            object Init: MovieDetailViewState()
            data class Loading(val isLoading: Boolean) : MovieDetailViewState()
            data class Succes(val data: Movie) : MovieDetailViewState()
            data class Error(val error: UiText) : MovieDetailViewState()
        }

        sealed class MovieFavMovieState {
            object Init: MovieFavMovieState()
            data class LoadingFav(val isLoading: Boolean) : MovieFavMovieState()
            data class SuccessFav(val isVisible: Boolean) : MovieFavMovieState()
            data class ErrorFav(val error: UiText) : MovieFavMovieState()
        }

    }

