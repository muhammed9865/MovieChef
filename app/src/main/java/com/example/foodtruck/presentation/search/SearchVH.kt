package com.example.foodtruck.presentation.search

import androidx.recyclerview.widget.RecyclerView
import com.example.foodtruck.R
import com.example.foodtruck.data.model.trending.Result
import com.example.foodtruck.databinding.ListItemSearchResultBinding
import com.example.foodtruck.presentation.home.adapter.TransferMovie
import com.example.foodtruck.presentation.util.MovieUtil
import kotlin.math.floor

class SearchVH(private val binding: ListItemSearchResultBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Result, transferMovie: TransferMovie?) {
        binding.apply {
            movieName.text = item.original_title
            movieDate.text = item.release_date
            loadRatePb(item.vote_average)
            MovieUtil.loadImage(binding.movieImage, item.poster_path)
            movieRateText.text = item.vote_average.toString()
            movieImage.setOnClickListener {
                transferMovie?.onMovieTransferred(item.id)
            }

        }
    }


    private fun loadRatePb(progress: Double) {
        val pb = binding.movieRatePb
        val context = itemView.context
        pb.progress = floor(progress).toInt()
        when {
            progress >= 7 -> {
                pb.setIndicatorColor(context.getColor(R.color.light_green))
            }
            progress > 5 -> {
                pb.setIndicatorColor(context.getColor(R.color.yellow))
            }
            else -> {
                pb.setIndicatorColor(context.getColor(R.color.light_grey))
            }
        }
    }


}