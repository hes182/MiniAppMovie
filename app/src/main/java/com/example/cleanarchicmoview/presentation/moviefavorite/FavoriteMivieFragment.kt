package com.example.cleanarchicmoview.presentation.moviefavorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchicmoview.R
import com.example.cleanarchicmoview.common.extension.addSimpleVerticalDecoration
import com.example.cleanarchicmoview.common.utils.Constants
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.databinding.FragmentFavoriteMivieBinding
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.presentation.movie_detail.MovieDetailActivity
import com.example.cleanarchicmoview.presentation.moviefavdetail.MovieFavoriteDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FavoriteMivieFragment : Fragment() {
    private var _bindig: FragmentFavoriteMivieBinding? = null
    private val viewModel: FavoriteMovieViewModel by viewModels()
    private val binding get() = _bindig!!
    private val page = 1
    private lateinit var adapterFavMovie: MovieFavAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindig = FragmentFavoriteMivieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavMovies(page)
        setListData()
        setObserversData()
    }

    private fun setObserversData() {
        viewModel.getViewState().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handlerChangeState(state) }.launchIn(lifecycleScope)
    }

    private fun handlerChangeState(state: FavoriteMovieViewModel.FavoriteViewState) {
        when(state) {
            is FavoriteMovieViewModel.FavoriteViewState.Init -> Unit
            is FavoriteMovieViewModel.FavoriteViewState.Loading -> handlerLoading(state.isLoading)
            is FavoriteMovieViewModel.FavoriteViewState.Success -> handlerSuccess(state.data)
            is FavoriteMovieViewModel.FavoriteViewState.Error -> handlerError(state.error)
            is FavoriteMovieViewModel.FavoriteViewState.SuccessWithEmptyData -> Unit
        }
    }

    private fun handlerSuccess(data: List<Movie>) {
        if (data == null || data.isEmpty()) {
            adapterFavMovie.setItemsData(emptyList())
        }
        adapterFavMovie.setItemsData(data)
    }

    private fun handlerLoading(isLoading: Boolean) {
        binding.progressBarFavmovie.isVisible = isLoading
    }

    private fun handlerError(error: UiText) {
        Toast.makeText(requireContext(), error.asString(requireContext()), Toast.LENGTH_SHORT).show()
    }

    private fun setListData() {
        binding.rvFavMovies.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        binding.rvFavMovies.addSimpleVerticalDecoration(
            16,
            includeFirstItem = true,
            includeLastItem = true
        )
        adapterFavMovie = MovieFavAdapter(object : FavMovieItemListener{
            override fun onFavMovieClicked(idMovie: Long) {
                val bundle = Bundle().apply { putLong(Constants.ARG_ID, idMovie) }
                val intent = Intent(requireContext(), MovieFavoriteDetailActivity::class.java)
                intent.putExtras(bundle)
                activity?.startActivity(intent)
            }
        })
        binding.rvFavMovies.adapter = adapterFavMovie
    }


    override fun onDestroy() {
        super.onDestroy()
        _bindig = null
    }

    companion object {
        fun newInstance() : FavoriteMivieFragment{
            return FavoriteMivieFragment()
        }
    }
}
