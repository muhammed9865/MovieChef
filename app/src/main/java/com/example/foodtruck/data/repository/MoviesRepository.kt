package com.example.foodtruck.data.repository

import android.util.Log
import com.example.foodtruck.Constants
import com.example.foodtruck.data.api.ApiBuilder
import com.example.foodtruck.data.api.TMBDEndPoint
import com.example.foodtruck.data.model.trending.TrendingResponse
import retrofit2.await
import retrofit2.awaitResponse

class MoviesRepository {
    private fun api() = ApiBuilder.buildConnection(TMBDEndPoint::class.java)

    suspend fun getTrending(): TrendingResponse? {
        api().getTrending(Constants.API_KEY).awaitResponse().let { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    return it
                }
            }else{
                Log.d("api error", "getTrending: ${response.message()}")
            }
            return null

        }
    }

    companion object {
        private var instance: MoviesRepository? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: MoviesRepository()
        }
    }
}