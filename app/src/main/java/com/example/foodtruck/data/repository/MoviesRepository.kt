package com.example.foodtruck.data.repository

import android.util.Log
import com.example.foodtruck.Constants
import com.example.foodtruck.data.api.ApiBuilder
import com.example.foodtruck.data.api.TMBDEndPoint
import com.example.foodtruck.data.model.trending.Result
import com.example.foodtruck.data.model.trending.MoviesResponse
import retrofit2.await
import retrofit2.awaitResponse

class MoviesRepository {
    private fun api() = ApiBuilder.buildConnection(TMBDEndPoint::class.java)

    suspend fun getTrending(): MoviesResponse? {
        api().getTrending(Constants.API_KEY).awaitResponse().let { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    return it
                }
            } else {
                Log.d("api error", "getTrending: ${response.message()}")
            }
            return null

        }
    }

    suspend fun getExplore(page: Int, isAdult: Boolean, isEnglishOnly: Boolean): List<Result> {
        return if (isEnglishOnly)
            api().getExplore(Constants.API_KEY, page, isAdult, "en-US").await().results
        else
            api().getExplore(Constants.API_KEY, page, isAdult).await().results
    }


    suspend fun search(query: String, isAdult: Boolean) = api().searchMovies(Constants.API_KEY, query, isAdult).await()

    companion object {
        private var instance: MoviesRepository? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: MoviesRepository()
        }
    }
}