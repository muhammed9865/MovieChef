package com.example.foodtruck.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtruck.data.model.trending.Result
import com.example.foodtruck.databinding.ListItemMovieBinding
import com.example.foodtruck.databinding.ListItemSearchResultBinding
import com.example.foodtruck.presentation.search.SearchVH

class HomeAdapter(private val isSearch: Boolean) : ListAdapter<Result, RecyclerView.ViewHolder>(HomeDiffUtil()) {
    private var transferMovie: TransferMovie? = null

    override fun getItemViewType(position: Int): Int {
        return if (isSearch) {
            SEARCH_VH
        } else {
            HOME_VH
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HOME_VH) {
            val binding = ListItemMovieBinding.inflate(LayoutInflater.from(parent.context))
            HomeVH(binding)
        }else {
            val binding = ListItemSearchResultBinding.inflate(LayoutInflater.from(parent.context))
            SearchVH(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeVH -> holder.bind(getItem(position), transferMovie)
            is SearchVH -> holder.bind(getItem(position), transferMovie)
        }
    }

    fun setOnTransferMovie(transferMovie: TransferMovie) {
        this.transferMovie = transferMovie
    }


    companion object {
        private const val HOME_VH = 1
        private const val SEARCH_VH = 2
    }
}