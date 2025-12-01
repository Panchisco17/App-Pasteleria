package com.example.app_pasteleria.data.remote

import com.example.app_pasteleria.data.model.Catalogo
import com.example.app_pasteleria.data.model.Postre
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET(value = "94b871072fe0570ecc07")
    suspend fun obtenerPostre(): List<Postre>

    @POST(value = "16296d63cab00926477e")
    suspend fun enviarPedido(@Body pedido: List<Catalogo>): List<Catalogo>
}