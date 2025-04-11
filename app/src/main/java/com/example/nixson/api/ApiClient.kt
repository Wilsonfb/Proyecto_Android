package com.example.nixson.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL_API_KOTLIN = "http://localhost:8080/api/"


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_API_KOTLIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitKotlinApi: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_API_KOTLIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ControladorCategoria by lazy {
        retrofit.create(ControladorCategoria::class.java)
    }


    val kotlinApiService: ControladorCategoria by lazy {
        retrofitKotlinApi.create(ControladorCategoria::class.java)
    }

}