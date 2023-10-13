package com.example.cleanarchicmoview.presentation.movie_detail

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.cleanarchicmoview.common.extension.runTimeToReadableDuration
import com.example.cleanarchicmoview.common.extension.toFullImageLink
import com.example.cleanarchicmoview.common.utils.Constants.Companion.ARG_ID
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.data.local.MovieDao
import com.example.cleanarchicmoview.data.remote.models.MovieDto
import com.example.cleanarchicmoview.databinding.ActivityFragmentMovieDetailBinding
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.domain.repositories.MovieRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: ActivityFragmentMovieDetailBinding
    private lateinit var bundle: Bundle
    private var movieId: Long? =0
    private lateinit var dao: MovieDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {
            bundle = intent.extras!!
            movieId = bundle.getLong(ARG_ID)
        }

        listener()
        setupObservers()
        setUpObserverFavMov()
        init()
        getFavMovie(movieId!!)
    }

    private fun setupObservers() {
        viewModel.getViewSet().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }.launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: MovieDetailViewModel.MovieDetailViewState) {
        when (state) {
            is MovieDetailViewModel.MovieDetailViewState.Error -> handleError(state.error)
            is MovieDetailViewModel.MovieDetailViewState.Init -> MovieDetailViewModel.MovieDetailViewState.Init
            is MovieDetailViewModel.MovieDetailViewState.Loading -> handleLoading(state.isLoading)
            is MovieDetailViewModel.MovieDetailViewState.Succes -> handleSuccess(state.data)
        }
    }

    private fun handleSuccess(data: Movie) {
        binding.tvvlTitle.text = data.title
        binding.tvvlDescription.text = data.overview
        Glide.with(this).load(data.posterPath.toFullImageLink())
            .into(binding.ivMovie)
        binding.ivAddPlaylist.setOnClickListener {
            setInsertData(data)
            Handler().postDelayed({
                handleLoading(true)
                getFavMovie(data.id)
            },1000)
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.pbDetMovie.isVisible = isLoading
    }

    private fun handleError(error: UiText) = Toast.makeText(
        this,
        error.asString(this),
        Toast.LENGTH_SHORT
    ).show()

    private fun init() = viewModel.getMovie(id = movieId!!)

    private fun listener() = binding.ivBackDetMovie.setOnClickListener{ finish() }

    private fun setInsertData(data: Movie) {
        viewModel.insertFavMovie(data)
    }

    private fun getFavMovie(id: Long) = viewModel.getMovieFavById(id)

    private fun setUpObserverFavMov() {
        viewModel.getViewSetFavMovie().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> setHandlerFavMovie(state) }.launchIn(lifecycleScope)
    }

    private fun setHandlerFavMovie(state: MovieDetailViewModel.MovieFavMovieState) {
        when(state) {
            is MovieDetailViewModel.MovieFavMovieState.Init -> MovieDetailViewModel.MovieFavMovieState.Init
            is MovieDetailViewModel.MovieFavMovieState.ErrorFav -> handleError(state.error)
            is MovieDetailViewModel.MovieFavMovieState.LoadingFav -> handleLoading(state.isLoading)
            is MovieDetailViewModel.MovieFavMovieState.SuccessFav -> setSuccesHandlerFavMov(state.isVisible)
        }
    }

    private fun setSuccesHandlerFavMov(isVisible: Boolean) {
        if (isVisible) {
            binding.ivAddPlaylist.visibility = View.GONE
            }else {
            binding.ivAddPlaylist.visibility = View.VISIBLE
        }
    }


}