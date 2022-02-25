package com.example.foodtruck.presentation.home

import com.example.foodtruck.data.model.trending.Result
import com.example.foodtruck.data.model.trending.MoviesResponse

sealed class HomeViewStates {
    object Idle: HomeViewStates()
    object Loading: HomeViewStates()
    data class ShowTrending(val movies: MoviesResponse): HomeViewStates()
    data class ShowExplore(val movies: List<Result>): HomeViewStates()
    data class Error(val message: String): HomeViewStates()
}