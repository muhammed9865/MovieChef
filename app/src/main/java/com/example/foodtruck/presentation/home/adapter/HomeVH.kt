package com.example.foodtruck.presentation.home.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.foodtruck.Constants
import com.example.foodtruck.R
import com.example.foodtruck.data.model.trending.Result
import com.example.foodtruck.databinding.ListItemTrendingBinding
import kotlinx.coroutines.*
import kotlin.math.floor

class HomeVH(private val binding: ListItemTrendingBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Result) {
        binding.apply {
            movieName.text = item.original_title
            movieDate.text = item.release_date
            loadImage(Constants.TMBD_IMAGE_PATH + item.poster_path)
            loadRatePb(item.vote_average)
            movieRateText.text = item.vote_average.toString()

        }
    }

    private fun loadImage(imageUrl: String) {
        Log.d("image loading", "loadImage: $imageUrl")
        Glide.with(itemView.context)
            .load(imageUrl)
            .centerCrop()
            .into(binding.movieImage)

    }

    private fun loadRatePb(progress: Double) {
        val pb = binding.movieRatePb
        val context = itemView.context
        pb.progress = floor(progress).toInt()
        when {
            progress >= 7.5 -> {
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