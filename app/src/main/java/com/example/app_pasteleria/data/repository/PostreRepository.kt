package com.example.app_pasteleria.data.repository

import com.example.app_pasteleria.data.model.Postre
import com.example.app_pasteleria.data.remote.RetrofitInstance
class PostreRepository {
    suspend fun getPosts(): List<Postre> {
        return RetrofitInstance.api.obtenerPostre()
    }
}