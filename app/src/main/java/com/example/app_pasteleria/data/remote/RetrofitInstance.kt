package com.example.app_pasteleria.data.remote

import com.example.app_pasteleria.data.remote.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
// Singleton que contiene la configuración de Retrofit
object RetrofitInstance {

    private const val BASE_URL = "https://api.npoint.io/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}