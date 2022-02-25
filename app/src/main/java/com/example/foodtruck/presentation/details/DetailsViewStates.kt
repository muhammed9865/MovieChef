package com.example.foodtruck.presentation.details

import com.example.foodtruck.data.model.MovieModel


sealed class DetailsViewStates {
    object Idle: DetailsViewStates()
    object Loading: DetailsViewStates()
    data class ShowMovieDetails(val movie: MovieModel): DetailsViewStates()
    data class Error(val message: String): DetailsViewStates()

}