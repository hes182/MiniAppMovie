package com.example.cleanarchicmoview.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleanarchicmoview.common.extension.toFullImageLink
import com.example.cleanarchicmoview.databinding.ListItemMovieBinding
import com.example.cleanarchicmoview.domain.models.Movie

class MovieAdapter(private val listener: MovieItemListener) :
    RecyclerView.Adapter<ViewModel>(){

    private val data = ArrayList<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Movie>) {
        this.data.clear()
        this.data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val binding = ListItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewModel(binding, listener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewModel, position: Int)  = with(holder){
        bind(data[position])
    }
}

class ViewModel(
    private val binding: ListItemMovieBinding,
    private val listener: MovieItemListener
): RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var movie: Movie

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(item: Movie) {
        this.movie = item
        binding.tvTitle.text = item.title
        Glide.with(itemView.context)
            .load(item.posterPath.toFullImageLink())
            .into(binding.ivMovie)
        binding.tvOverview.text = item.overview
    }

    override fun onClick(v: View?) = listener.onMovieClicked(movie.id)
}

interface MovieItemListener {
    fun onMovieClicked(movieId: Long)
}