package com.example.foodtruck.data.api

import com.example.foodtruck.data.model.trending.TrendingResponse
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface TMBDEndPoint {

    @GET("/3/movie/popular")
    @Headers("Content-Type: application/json")
    fun getTrending(
        @Query("api_key") token: String
    ):Call<TrendingResponse>
}