package com.example.foodtruck.presentation.home

import com.example.foodtruck.data.model.trending.TrendingResponse

sealed class HomeViewStates {
    object Idle: HomeViewStates()
    object Loading: HomeViewStates()
    data class ShowTrending(val movies: TrendingResponse): HomeViewStates()
    data class Error(val message: String): HomeViewStates()
}