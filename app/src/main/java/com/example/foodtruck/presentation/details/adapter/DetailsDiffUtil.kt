package com.example.foodtruck.presentation.details.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.foodtruck.data.model.credits.Cast
import com.example.foodtruck.data.model.credits.Crew

class DetailsDiffUtil: DiffUtil.ItemCallback<DetailsBase>() {
    override fun areItemsTheSame(oldItem: DetailsBase, newItem: DetailsBase): Boolean {
        when (oldItem) {
            is Crew -> return oldItem.id == (newItem as Crew).id
            is Cast -> return oldItem.id == (newItem as Cast).id
        }
        return false
    }

    override fun areContentsTheSame(oldItem: DetailsBase, newItem: DetailsBase): Boolean {
        when (oldItem) {
            is Crew -> return oldItem == (newItem as Crew)
            is Cast -> return oldItem == (newItem as Cast)
        }
        return false
    }
}