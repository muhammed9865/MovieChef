package com.example.foodtruck.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodtruck.data.repository.MoviesRepository
import com.example.foodtruck.presentation.home.HomeIntents
import com.example.foodtruck.presentation.home.HomeViewStates
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val repo: MoviesRepository) : ViewModel() {
    val intentChannel = Channel<HomeIntents>(Channel.UNLIMITED)
    private val _homeStates = MutableStateFlow<HomeViewStates>(HomeViewStates.Idle)
    val homeStates: StateFlow<HomeViewStates> get() = _homeStates
    var pageNumber = 1
    private var isAdultContent = false
    private var isTopRated = false

    init {
        processIntents()
    }

    private fun processIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is HomeIntents.GetTrendingMovies -> loadTrending()
                    is HomeIntents.Explore -> loadExplore()
                    is HomeIntents.PageUp -> {
                        pageNumber++
                        loadExplore()
                    }
                    is HomeIntents.PageDown -> {
                        pageNumber--
                        loadExplore()
                    }
                    is HomeIntents.ShowTopRated -> {
                        isTopRated = !isTopRated
                        loadExplore()
                    }
                    is  HomeIntents.ChangeAdultState -> {
                        isAdultContent = !isAdultContent
                        loadExplore()
                    }
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
                } catch (e: Exception) {
                    HomeViewStates.Error(e.message!!)
                }

        }
    }

    private fun loadExplore() {
        viewModelScope.launch {
            _homeStates.value =
                try {
                    HomeViewStates.Loading
                    repo.getExplore(pageNumber, isAdultContent, false).let {
                        if (isTopRated)
                            HomeViewStates.ShowExplore(it.filter { result -> result.vote_average > 7.5 })
                        else
                            HomeViewStates.ShowExplore(it)
                    }
                } catch (e: Exception) {
                    HomeViewStates.Error(e.message!!)
                }
        }
    }
}