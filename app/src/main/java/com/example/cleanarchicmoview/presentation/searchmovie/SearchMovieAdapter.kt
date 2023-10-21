package com.example.cleanarchicmoview.presentation.searchmovie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleanarchicmoview.common.extension.toFullImageLink
import com.example.cleanarchicmoview.databinding.ListItemSearchmovieBinding
import com.example.cleanarchicmoview.domain.models.Movie

class SearchMovieAdapter(
    private val listener: MovieListner
) : RecyclerView.Adapter<SearchMovieAdapter.ViewModel>() {
    private val listData = ArrayList<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    fun setDataSearch(data: List<Movie>) {
        this.listData.clear()
        this.listData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val binding = ListItemSearchmovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewModel(binding, listener)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        with(holder) {Bind(listData[position])}
    }

    class ViewModel(
        private val binding : ListItemSearchmovieBinding,
        private val listener: MovieListner
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var movie: Movie

        init {
            binding.root.setOnClickListener(this)
        }

        fun Bind(data: Movie) {
            this.movie = data
            binding.tvTitle.text = data.title
            binding.tvOverview.text = data.overview
            Glide.with(itemView.context)
                .load(data.posterPath.toFullImageLink())
                .into(binding.ivMovie)
        }

        override fun onClick(p0: View?) {
            listener.onClickSearchMovie(movie.id)
        }
    }

}

interface MovieListner {
    fun onClickSearchMovie(movieId: Long)
}
