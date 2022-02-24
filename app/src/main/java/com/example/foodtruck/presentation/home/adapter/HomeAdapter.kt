package com.example.foodtruck.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.foodtruck.data.model.trending.Result
import com.example.foodtruck.databinding.ListItemTrendingBinding

class HomeAdapter: ListAdapter<Result, HomeVH>(HomeDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeVH {
        val binding = ListItemTrendingBinding.inflate(LayoutInflater.from(parent.context))
        return HomeVH(binding)
    }

    override fun onBindViewHolder(holder: HomeVH, position: Int) {
        holder.bind(getItem(position))
    }
}