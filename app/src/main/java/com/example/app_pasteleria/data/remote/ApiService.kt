package com.example.app_pasteleria.data.remote

import com.example.app_pasteleria.data.model.Post
import retrofit2.http.GET
// Esta interfaz define los endpoints HTTP
interface ApiService {
    // Define una solicitud GET al endpoint /posts
    @GET(value = "/posts")
    suspend fun getPosts(): List<Post>
}