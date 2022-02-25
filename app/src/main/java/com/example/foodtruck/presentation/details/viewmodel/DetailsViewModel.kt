package com.example.foodtruck.presentation.details.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodtruck.data.model.MovieModel
import com.example.foodtruck.data.model.credits.MovieDetailsCredits
import com.example.foodtruck.data.model.movie.MovieDetails
import com.example.foodtruck.data.repository.MoviesDetailsRepository
import com.example.foodtruck.presentation.details.DetailsIntents
import com.example.foodtruck.presentation.details.DetailsViewStates
import com.google.gson.Gson
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailsViewModel : ViewModel() {
    private val repo = MoviesDetailsRepository()

    val intentChannel = Channel<DetailsIntents>(Channel.UNLIMITED)

    private val _detailsStates = MutableStateFlow<DetailsViewStates>(DetailsViewStates.Idle)
    val states: StateFlow<DetailsViewStates> get() = _detailsStates
    private var movieId = 63565
    init {
        processIntents()
    }

    private fun processIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is DetailsIntents.GetMovie -> {
                        movieId = intent.movie_id
                        getMovieDetails()
                    }
                }
            }
        }
    }

    private fun getMovieDetails() {
        viewModelScope.launch {
            try {
                _detailsStates.value = DetailsViewStates.Loading
                val movieDetails = flow { emit(repo.getMovieDetails(movieId)) }
                val creditsDetails = flow { emit(repo.getMovieCredits(movieId))
                }

                movieDetails.zip(creditsDetails) { details, credits ->
                    MovieModel.Builder(details, credits).build()

                }.collect { movie ->
                    _detailsStates.value = DetailsViewStates.ShowMovieDetails(movie)
                }

            } catch (e: Exception) {
                _detailsStates.value =DetailsViewStates.Error(e.message!!)
            }

        }
    }



    companion object {
        private const val TAG = "DetailsViewModel"
    }

}