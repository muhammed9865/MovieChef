package com.example.foodtruck.presentation.search

sealed class SearchIntents {
    data class Search(val query: String): SearchIntents()
    object ShowTopRated: SearchIntents()
    object ChangeAdultState: SearchIntents()
}