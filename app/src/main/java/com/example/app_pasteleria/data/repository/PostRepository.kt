package com.example.app_pasteleria.data.repository

import com.example.app_pasteleria.data.model.Post
import com.example.app_pasteleria.data.remote.RetrofitInstance

class PostRepository {
    // Función que obtiene los posts desde la API
    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }
}