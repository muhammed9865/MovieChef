package com.example.foodtruck.data.api

import com.example.foodtruck.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


object ApiBuilder {
    private val client = OkHttpClient
        .Builder()
        .build()

    private val gson = GsonBuilder().setLenient().create()
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.TMBD_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun <T> buildConnection(service: Class<T>): T = retrofit.create(service)
}