package com.example.cleanarchicmoview.presentation.searchmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchicmoview.common.extension.addSimpleVerticalDecoration
import com.example.cleanarchicmoview.common.utils.Constants
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.databinding.ActivitySearchBinding
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.presentation.movie_detail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchMovieActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    private val viewMocel: SearchMovieViewModel by viewModels()
    private lateinit var adapter: SearchMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etSearch.requestFocus()
        setSearchList()
        setObserverSearch()
        setListenerSearch()
    }

    private fun handlerSuccess(data: List<Movie>) {
        binding.viewError.tvError.visibility = View.GONE
        adapter.setDataSearch(data)
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun handleError(error: UiText) {
        binding.viewError.tvError.text = error.asString(this)
        adapter.setDataSearch(arrayListOf())
        binding.viewError.tvError.visibility = View.VISIBLE
    }

    private fun handleState(state: SearchMovieViewModel.SearchViewState) {
        when(state) {
            is SearchMovieViewModel.SearchViewState.Init -> Unit
            is SearchMovieViewModel.SearchViewState.Loading -> handleLoading(state.isLoading)
            is SearchMovieViewModel.SearchViewState.Error -> handleError(state.error)
            is SearchMovieViewModel.SearchViewState.Success -> handlerSuccess(state.data)
        }
    }

    private fun setObserverSearch() {
        viewMocel.getViewState().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleState(state) }.launchIn(lifecycleScope)
    }

    private fun setSearchList() {
        binding.rvMovies.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMovies.addSimpleVerticalDecoration(
            16,
            includeFirstItem = true,
            includeLastItem = true
        )
        adapter = SearchMovieAdapter(object : MovieListner {
            override fun onClickSearchMovie(movieId: Long) {
                val bundle = Bundle().apply { putLong(Constants.ARG_ID, movieId) }
                val intent = Intent(this@SearchMovieActivity, MovieDetailActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
                finish()
            }
        })
        binding.rvMovies.adapter = adapter
    }

    private fun setListenerSearch() {
        binding.ivBack.setOnClickListener { finish() }
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideSoftKeyboard()
            }
            true
        }
        binding.etSearch.addTextChangedListener { text ->
            if (text != null && text.length > 1) {
                viewMocel.searchMovie(text.toString())
            }
        }
    }

    private fun hideSoftKeyboard() {
        val inputMethMan: InputMethodManager = getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethMan.isAcceptingText) {
            inputMethMan.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    companion object {
        fun createSimpleIntent(context: Context): Intent = Intent(
            context,
            SearchMovieActivity::class.java
        )
    }
}