package com.example.foodtruck.presentation.util

import android.widget.ImageView
import coil.load
import com.example.foodtruck.Constants

abstract class MovieUtil {
    companion object {
        fun loadImage(imageView: ImageView, source: String?) {
            source?.let {
                imageView.load(Constants.TMBD_IMAGE_PATH + it)
            }
        }
    }
}