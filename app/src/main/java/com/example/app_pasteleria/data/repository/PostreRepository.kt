package com.example.app_pasteleria.data.repository

import com.example.app_pasteleria.data.model.Postre
import com.example.app_pasteleria.data.remote.RetrofitInstance
// Este repositorio se encarga de acceder a los datos usando Retrofit
class PostreRepository {
    // Función que obtiene los posts desde la API
    suspend fun getPosts(): List<Postre> {
        return RetrofitInstance.api.obtenerPostre()
    }
}