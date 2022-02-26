package com.example.foodtruck.presentation.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtruck.data.model.credits.Cast
import com.example.foodtruck.data.model.credits.Crew
import com.example.foodtruck.databinding.ListItemCrewBinding

class DetailsAdapter(private val isCastList: Boolean) :
    ListAdapter<DetailsBase, RecyclerView.ViewHolder>(DetailsDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return if (isCastList) {
            CAST_LIST
        } else CREW_LIST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ListItemCrewBinding.inflate(LayoutInflater.from(parent.context))
        return if (viewType == CREW_LIST)
            CrewViewHolder(binding)
        else
            CastViewHolder(binding)


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CrewViewHolder -> holder.bind(getItem(position) as Crew)
            is CastViewHolder -> holder.bind(getItem(position) as Cast)
        }
    }



    companion object {
        private const val CAST_LIST = 1
        private const val CREW_LIST = 2
    }
}