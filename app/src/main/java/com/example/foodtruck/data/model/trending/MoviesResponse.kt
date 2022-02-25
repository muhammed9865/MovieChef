package com.example.foodtruck.data.model.trending

data class MoviesResponse(
    val page: Int, // 1
    val results: List<Result>,
    val total_pages: Int, // 32466
    val total_results: Int // 649314
)