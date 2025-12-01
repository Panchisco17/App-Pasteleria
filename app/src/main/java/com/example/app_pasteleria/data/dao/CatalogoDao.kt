package com.example.app_pasteleria.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.app_pasteleria.data.model.Catalogo
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogoDao {
    @Insert
    suspend fun insertarCatalogo(catalogo: Catalogo)

    @Query("SELECT * FROM catalogo")
    fun obtenerCatalogo(): Flow<List<Catalogo>>

    // Esta es la función clave que te falta para que funcione limpiarCarrito
    @Query("DELETE FROM catalogo")
    suspend fun borrarTodo()

    @Update
    suspend fun actualizarCatalogo(catalogo: Catalogo)

    @Delete
    suspend fun eliminarCatalogo(catalogo: Catalogo)
}