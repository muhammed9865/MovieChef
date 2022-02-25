package com.example.foodtruck.data.repository

import com.example.foodtruck.Constants
import com.example.foodtruck.data.api.ApiBuilder
import com.example.foodtruck.data.api.MovieDetailsEndPoint
import com.example.foodtruck.data.api.TMBDEndPoint
import retrofit2.await

class MoviesDetailsRepository {
    private fun api() = ApiBuilder.buildConnection(MovieDetailsEndPoint::class.java)

    suspend fun getMovieDetails(movie_id: Int) = api().getMovieDetails(Constants.API_KEY, movie_id).await()

    suspend fun getMovieCredits(movie_id: Int) = api().getMovieCredits(Constants.API_KEY, movie_id).await()

    companion object {
        private var instance: MoviesRepository? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: MoviesDetailsRepository()
        }
    }
}