package com.example.app_pasteleria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_pasteleria.data.model.Catalogo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogoViewModel : ViewModel(){

    // Tu lista de productos que ya tenías definida
    private val _pasteles = MutableStateFlow<List<Catalogo>>(emptyList())
    val pasteles: StateFlow<List<Catalogo>> = _pasteles.asStateFlow()

    fun guardarPastel(catalogo: Catalogo){
        viewModelScope.launch {
            // Tu lógica original para guardar en memoria (en el StateFlow)
            val nuevaLista = _pasteles.value + catalogo
            _pasteles.value = nuevaLista
        }
    } // fin guardarPastel

    // --- NUEVA FUNCIÓN PARA LA LÓGICA DE DESCUENTO ---

    fun calcularPrecioFinal(precioOriginal: String, codigoDescuento: String?): String {
        val precioBase = precioOriginal.toDoubleOrNull() ?: 0.0

        if (codigoDescuento.equals("FELICES50", ignoreCase = true)) {
            val descuento = precioBase * 0.10 // 10%
            val precioFinal = precioBase - descuento

            return precioFinal.toInt().toString()
        }
        return precioOriginal
    }
} // fin class