package com.example.foodtruck.presentation.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.foodtruck.data.model.trending.Result

class HomeDiffUtil: DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }

}