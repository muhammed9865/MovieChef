package com.example.foodtruck.presentation.search.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodtruck.data.repository.MoviesRepository
import com.example.foodtruck.presentation.search.SearchIntents
import com.example.foodtruck.presentation.search.SearchViewStates
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchViewModel : ViewModel() {
    private val repo = MoviesRepository.getInstance()
    val intentChannel = Channel<SearchIntents>(Channel.UNLIMITED)
    private val _viewStates = MutableStateFlow<SearchViewStates>(SearchViewStates.Idle)
    val viewStates: StateFlow<SearchViewStates> get() = _viewStates

    private var query = ""
    private var isAdult = false
    private var isTopRated = false

    init {
        processIntent()
    }

    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is SearchIntents.Search -> {
                        query = intent.query
                        if (query.isNotEmpty())
                            searchApi()
                    }
                    is SearchIntents.ShowTopRated -> {
                        isTopRated = !isTopRated
                        if (query.isNotEmpty())
                            searchApi()
                    }
                    is SearchIntents.ChangeAdultState -> {
                        isAdult = !isAdult
                        if (query.isNotEmpty())
                            searchApi()
                    }

                }
            }
        }
    }

    private fun searchApi() {
        viewModelScope.launch {
            _viewStates.value =
                try {
                    SearchViewStates.Searching
                    if (!isTopRated) {
                        repo.search(query, isAdult).results.let {
                            Log.d("render", "searchApi: $it")
                            SearchViewStates.SearchResult(it)
                        }
                    } else {
                        repo.search(query, isAdult).results.let {
                            Log.d("render", "searchApi: $it")
                            SearchViewStates.SearchResult(it.filter { show -> show.vote_average > 7 })
                        }
                    }
                } catch (e: Exception) {
                    SearchViewStates.Error(e.message!!)
                }
        }
    }
}