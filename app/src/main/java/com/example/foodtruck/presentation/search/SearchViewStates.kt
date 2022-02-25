package com.example.foodtruck.presentation.search

import com.example.foodtruck.data.model.trending.Result

sealed class SearchViewStates {
    object Idle: SearchViewStates()
    object Searching: SearchViewStates()
    data class SearchResult(val movies: List<Result>): SearchViewStates()
    data class Error(val message: String): SearchViewStates()
}