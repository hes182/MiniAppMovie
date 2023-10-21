package com.example.cleanarchicmoview.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchicmoview.common.extension.addSimpleVerticalDecoration
import com.example.cleanarchicmoview.common.utils.Constants.Companion.ARG_ID
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.databinding.ActivityFhomeFragmentBinding
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.presentation.movie_detail.MovieDetailActivity
import com.example.cleanarchicmoview.presentation.searchmovie.SearchMovieActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FHomeFragment : Fragment() {
    private var _binding: ActivityFhomeFragmentBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var adapter: MovieAdapter
    private val page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityFhomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()
        setupObservers()
        viewModel.getMovies(page)
        setListener()
    }


    private fun setupObservers() {
        viewModel.getViewState().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }.launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: HomeViewModel.HomeViewState) {
        when(state) {
            is HomeViewModel.HomeViewState.Init -> Unit
            is HomeViewModel.HomeViewState.Loading -> handleLoading(state.isLoading)
            is HomeViewModel.HomeViewState.Success -> handleSuccess(state.data)
            is HomeViewModel.HomeViewState.SuccessWithEmptyData -> Unit
            is HomeViewModel.HomeViewState.Error -> handleError(state.error)

        }
    }

    private fun handleSuccess(list: List<Movie>) = adapter.setItems(list)

    private fun handleLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    private fun handleError(error: UiText) =
        Toast.makeText(requireContext(), error.asString(requireContext()), Toast.LENGTH_SHORT).show()

    private fun setUpList() {
        binding.rvMovies.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        binding.rvMovies.addSimpleVerticalDecoration(
            16,
            includeFirstItem = true,
            includeLastItem = true
        )
        adapter = MovieAdapter(object : MovieItemListener {
            override fun onMovieClicked(movieId: Long) {
                val bundle = Bundle().apply { putLong(ARG_ID, movieId) }
                val intent = Intent(requireContext(), MovieDetailActivity::class.java)
                intent.putExtras(bundle)
                activity?.startActivity(intent)
            }
        })
        binding.rvMovies.adapter = adapter
    }

    private fun setListener() =
        binding.cvSearch.setOnClickListener {
            startActivity(SearchMovieActivity.createSimpleIntent(requireContext()))
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun NewInstance() : FHomeFragment {
            return FHomeFragment()
        }
    }
}