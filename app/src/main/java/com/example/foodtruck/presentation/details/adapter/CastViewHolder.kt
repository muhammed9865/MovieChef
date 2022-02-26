package com.example.foodtruck.presentation.details.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodtruck.Constants
import com.example.foodtruck.data.model.credits.Cast
import com.example.foodtruck.databinding.ListItemCrewBinding

class CastViewHolder(private val binding: ListItemCrewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(cast: Cast) {
        binding.apply {
            crewName.text = cast.name
            crewJob.text = cast.character
            cast.profile_path?.let {
                crewImage.load(Constants.TMBD_IMAGE_PATH + it)
            }
        }
    }

}