package com.example.foodtruck.presentation.details.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.foodtruck.Constants
import com.example.foodtruck.R
import com.example.foodtruck.data.model.credits.Crew
import com.example.foodtruck.databinding.ListItemCrewBinding

class CrewViewHolder(private val binding: ListItemCrewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Crew) {
        binding.apply {
            loadImage(item.profile_path)
            binding.apply {
                crewName.text = item.name
                crewJob.text = item.job
            }
        }
    }

    private fun loadImage(url: String?) {
        url?.let {
            binding.crewImage.load(Constants.TMBD_IMAGE_PATH + it)
        }

    }
}