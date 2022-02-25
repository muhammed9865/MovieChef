package com.example.foodtruck.presentation.home

sealed class HomeIntents {
    object GetTrendingMovies : HomeIntents()
    object Explore : HomeIntents()

    object ChangeAdultState : HomeIntents()
    object ShowTopRated: HomeIntents()
    object PageUp : HomeIntents()
    object PageDown : HomeIntents()
}