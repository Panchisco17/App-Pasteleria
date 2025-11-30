package com.example.app_pasteleria.data.repository

import com.example.app_pasteleria.data.dao.CatalogoDao
import com.example.app_pasteleria.data.model.Catalogo
import com.example.app_pasteleria.data.model.Postre
import com.example.app_pasteleria.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class CatalogoRepository (private val catalogoDao: CatalogoDao) {

    suspend fun obtenerPasteles(): List<Postre> {

        val respuestaApi = RetrofitInstance.api.obtenerPostre()

        return respuestaApi.map {
            Postre(
                nombre = it.nombre,
                precio = it.precio,
                descripcion = it.descripcion
            )}


    }

    suspend fun insertarCatalogo(catalogo: Catalogo) {
        catalogoDao.insertarCatalogo(catalogo)
    }

    fun obtenerCatalogo(): Flow<List<Catalogo>>{
        return catalogoDao.obtenerCatalogo()
    }
}