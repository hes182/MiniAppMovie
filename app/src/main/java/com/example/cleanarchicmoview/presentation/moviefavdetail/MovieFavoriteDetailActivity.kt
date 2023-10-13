package com.example.cleanarchicmoview.presentation.moviefavdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.cleanarchicmoview.R
import com.example.cleanarchicmoview.common.extension.toFullImageLink
import com.example.cleanarchicmoview.common.utils.Constants.Companion.ARG_ID
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.databinding.ActivityMovieFavoriteDetailBinding
import com.example.cleanarchicmoview.domain.models.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieFavoriteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieFavoriteDetailBinding
    private lateinit var bundle: Bundle
    private var idMovie: Long? = 0
    private val viewModel: MovieFavViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {
            bundle = intent.extras!!
            idMovie = bundle.getLong(ARG_ID)
        }

        setUpObserver()
        listener()
        init()
    }

    private fun init() = viewModel.getfavMovieDetail(id = idMovie!!)

    private fun listener() = binding.ivBackDetMovie.setOnClickListener{finish()}

    private fun setHandlerLoading(isLoading: Boolean) {
        binding.pbDetMovie.isVisible = isLoading
    }

    private fun setUpObserver() {
        viewModel.getViewState().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> setHandlerStateChanged(state) }.launchIn(lifecycleScope)
    }

    private fun setHandlerStateChanged(state: MovieFavViewModel.favMovieDetState) {
        when(state) {
            is MovieFavViewModel.favMovieDetState.Init -> MovieFavViewModel.favMovieDetState.Init
            is MovieFavViewModel.favMovieDetState.Loading -> setHandlerLoading(state.isLoading)
            is MovieFavViewModel.favMovieDetState.Success -> setHundlerSucces(state.data)
            is MovieFavViewModel.favMovieDetState.Error -> setHandlerError(state.error)
        }
    }

    private fun setHundlerSucces(data: Movie) {
        binding.tvvlTitle.text = data.title
        binding.tvvlDescription.text = data.overview
        Glide.with(this).load(data.posterPath.toFullImageLink())
            .into(binding.ivMovie)
    }

    private fun setHandlerError(error: UiText) = Toast.makeText(
        this,
        error.asString(this),
        Toast.LENGTH_SHORT
    ).show()
}