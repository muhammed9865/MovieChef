package com.example.foodtruck.presentation.details

sealed class DetailsIntents {
    data class GetMovie(val movie_id: Int): DetailsIntents()
}