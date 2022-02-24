package com.example.foodtruck.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodtruck.data.model.trending.TrendingResponse
import com.example.foodtruck.data.repository.MoviesRepository
import com.example.foodtruck.presentation.home.HomeIntents
import com.example.foodtruck.presentation.home.HomeViewStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val repo: MoviesRepository) : ViewModel() {
    val intentChannel = Channel<HomeIntents>(Channel.UNLIMITED)

    private val _homeStates = MutableStateFlow<HomeViewStates>(HomeViewStates.Idle)
    val homeStates: StateFlow<HomeViewStates> get() = _homeStates

    init {
        processIntents()
    }

    private fun processIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is HomeIntents.GetTrendingMovies -> loadTrending()
                }
            }
        }
    }

    private fun loadTrending() {
        viewModelScope.launch {
            _homeStates.value =
                try {
                    HomeViewStates.Loading
                    repo.getTrending().let {
                        HomeViewStates.ShowTrending(it!!)
                    }
                }catch (e: Exception) {
                    HomeViewStates.Error(e.message!!)
                }

        }
    }
}