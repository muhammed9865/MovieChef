package com.example.foodtruck.presentation.details.adapter

import androidx.recyclerview.widget.RecyclerView
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
        Glide.with(itemView.context)
            .load(Constants.TMBD_IMAGE_PATH + url)
            .into(binding.crewImage)
            .onLoadFailed(itemView.context.getDrawable(R.drawable.ic_baseline_image_24))
    }
}