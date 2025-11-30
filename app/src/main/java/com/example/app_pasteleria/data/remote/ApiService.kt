package com.example.app_pasteleria.data.remote

import com.example.app_pasteleria.data.model.Postre
import retrofit2.http.GET
// Esta interfaz define los endpoints HTTP
interface ApiService {
    // Define una solicitud GET al endpoint /posts
    @GET(value = "94b871072fe0570ecc07")
    suspend fun obtenerPostre(): List<Postre>
}