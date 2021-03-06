package com.example.foodtruck.presentation.home.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodtruck.Constants
import com.example.foodtruck.R
import com.example.foodtruck.data.model.trending.Result
import com.example.foodtruck.databinding.ListItemMovieBinding
import com.example.foodtruck.presentation.util.MovieUtil
import kotlinx.coroutines.*
import kotlin.math.floor

class HomeVH(private val binding: ListItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Result, transferMovie: TransferMovie?) {
        binding.apply {
            movieName.text = item.original_title
            movieDate.text = item.release_date
            MovieUtil.loadImage(binding.movieImage, item.poster_path)
            loadRatePb(item.vote_average)
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