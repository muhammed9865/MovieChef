package com.example.foodtruck.data.api

import com.example.foodtruck.data.model.trending.MoviesResponse
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TMBDEndPoint {

    @GET("/3/movie/popular")
    @Headers("Content-Type: application/json")
    fun getTrending(
        @Query("api_key") token: String
    ):Call<MoviesResponse>

    @GET("/3/discover/movie")
    @Headers("Content-Type: application/json")
    fun getExplore(
        @Query("api_key") token: String,
        @Query("page") page: Int,
        @Query("include_adult") isAdult: Boolean,
        @Query("language") lang: String
    ):Call<MoviesResponse>

    @GET("/3/discover/movie")
    @Headers("Content-Type: application/json")
    fun getExplore(
        @Query("api_key") token: String,
        @Query("page") page: Int,
        @Query("include_adult") isAdult: Boolean,
    ):Call<MoviesResponse>

    @GET("/3/search/movie")
    fun searchMovies(
        @Query("api_key") token: String,
        @Query("query") searchQuery: String,
        @Query("include_adult") isAdult: Boolean,
    ): Call<MoviesResponse>
}