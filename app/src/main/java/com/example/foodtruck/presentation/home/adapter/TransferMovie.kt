package com.example.foodtruck.presentation.home.adapter

import android.widget.ImageView
import com.example.foodtruck.data.model.trending.Result

interface TransferMovie {
    fun onMovieTransferred(movie_image: ImageView, movie: Result)
}