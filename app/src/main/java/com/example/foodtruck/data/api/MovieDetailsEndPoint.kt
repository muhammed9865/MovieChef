package com.example.foodtruck.data.api

import com.example.foodtruck.data.model.credits.MovieDetailsCredits
import com.example.foodtruck.data.model.movie.MovieDetails
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsEndPoint {

    @GET("/3/movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") token: String,
    ): Call<MovieDetails>

    @GET("/3/movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") token: String,
    ): Call<MovieDetailsCredits>
}