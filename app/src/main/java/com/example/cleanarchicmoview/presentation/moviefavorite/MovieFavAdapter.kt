package com.example.cleanarchicmoview.presentation.moviefavorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleanarchicmoview.common.extension.toFullImageLink
import com.example.cleanarchicmoview.databinding.ListItemFavmovieBinding
import com.example.cleanarchicmoview.domain.models.Movie

class MovieFavAdapter(private val listener: FavMovieItemListener) :
    RecyclerView.Adapter<MovieFavAdapter.FavViewModel>() {
    private val dataList = ArrayList<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItemsData(data: List<Movie>) {
        this.dataList.clear()
        this.dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewModel {
        val binding = ListItemFavmovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavViewModel(binding, listener)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: FavViewModel, position: Int) = with(holder) {
        Bind(dataList[position])
    }

    class FavViewModel(
        private val binding: ListItemFavmovieBinding,
        private val listener: FavMovieItemListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var dataMovie: Movie

        override fun onClick(p0: View?) = listener.onFavMovieClicked(dataMovie.id)

        init {
            binding.root.setOnClickListener(this)
        }

        fun Bind(data: Movie) {
            this.dataMovie = data
            binding.tvFavTitle.text = data.title
            binding.tvFavOverview.text = data.overview
            Glide.with(itemView.context)
                .load(data.posterPath.toFullImageLink())
                .into(binding.ivFavMovie)
        }
    }
}

interface FavMovieItemListener {
    fun onFavMovieClicked(idMovie: Long)
}
