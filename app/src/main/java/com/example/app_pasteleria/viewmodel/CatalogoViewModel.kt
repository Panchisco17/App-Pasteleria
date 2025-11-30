package com.example.app_pasteleria.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_pasteleria.data.database.CatalogoDatabase
import com.example.app_pasteleria.data.model.Catalogo
import com.example.app_pasteleria.data.repository.CatalogoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Cambiamos ViewModel por AndroidViewModel(application) para tener acceso al Contexto
class CatalogoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CatalogoRepository

    // Estado del carrito (Base de datos local)
    private val _pasteles = MutableStateFlow<List<Catalogo>>(emptyList())
    val pasteles: StateFlow<List<Catalogo>> = _pasteles.asStateFlow()

    init {
        // 1. Obtenemos la base de datos usando el contexto de la aplicación
        val database = CatalogoDatabase.getDatabase(application)
        // 2. Obtenemos el DAO
        val dao = database.CatalogoDao()
        // 3. Inicializamos el repositorio manualmente
        repository = CatalogoRepository(dao)

        // 4. Observamos los datos del carrito
        viewModelScope.launch {
            repository.obtenerCatalogo().collect { items ->
                _pasteles.value = items
            }
        }
    }

    fun guardarPastel(catalogo: Catalogo) {
        viewModelScope.launch {
            repository.insertarCatalogo(catalogo)
        }
    }

    fun calcularPrecioFinal(precioOriginal: String, codigoDescuento: String?): String {
        val precioBase = precioOriginal.toDoubleOrNull() ?: 0.0

        if (codigoDescuento.equals("FELICES50", ignoreCase = true)) {
            val descuento = precioBase * 0.10 // 10% de descuento
            val precioFinal = precioBase - descuento
            return precioFinal.toInt().toString()
        }
        return precioOriginal
    }
}