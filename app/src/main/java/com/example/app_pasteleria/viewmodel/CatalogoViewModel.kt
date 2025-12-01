package com.example.app_pasteleria.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_pasteleria.data.database.CatalogoDatabase
import com.example.app_pasteleria.data.model.Catalogo
import com.example.app_pasteleria.data.repository.CatalogoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CatalogoRepository

    private val _pasteles = MutableStateFlow<List<Catalogo>>(emptyList())
    val pasteles: StateFlow<List<Catalogo>> = _pasteles.asStateFlow()

    private val _enviando = MutableStateFlow(false)
    val enviando: StateFlow<Boolean> = _enviando.asStateFlow()

    init {
        val database = CatalogoDatabase.getDatabase(application)
        val dao = database.CatalogoDao()
        repository = CatalogoRepository(dao)

        viewModelScope.launch {
            repository.obtenerCatalogo().collect { items ->
                _pasteles.value = items
            }
        }
    }

    private fun sincronizarNube() {
        viewModelScope.launch {
            delay(500)
            val listaActualizada = _pasteles.value

            repository.enviarPedidoInternet(listaActualizada)
        }
    }
    fun guardarPastel(catalogo: Catalogo) {
        viewModelScope.launch {
            repository.insertarCatalogo(catalogo)
            sincronizarNube()
        }
    }
    fun eliminarProducto(producto: Catalogo) {
        viewModelScope.launch {
            repository.eliminarProducto(producto)
            sincronizarNube()
        }
    }
    fun actualizarCantidad(producto: Catalogo, sumar: Boolean) {
        viewModelScope.launch {
            val cantidadActual = producto.cantidad.toIntOrNull() ?: 1
            val nuevaCantidad = if (sumar) cantidadActual + 1 else cantidadActual - 1

            if (nuevaCantidad > 0) {
                val precioTotalActual = producto.precio.toDoubleOrNull() ?: 0.0
                val precioUnitario = precioTotalActual / cantidadActual
                val nuevoPrecioTotal = (precioUnitario * nuevaCantidad).toInt().toString()

                val productoActualizado = producto.copy(
                    cantidad = nuevaCantidad.toString(),
                    precio = nuevoPrecioTotal
                )

                repository.actualizarProducto(productoActualizado)
                sincronizarNube()
            }
        }
    }
    fun calcularPrecioFinal(precioOriginal: String, codigoDescuento: String?): String {
        val precioBase = precioOriginal.toDoubleOrNull() ?: 0.0
        if (codigoDescuento.equals("FELICES50", ignoreCase = true)) {
            val descuento = precioBase * 0.10
            val precioFinal = precioBase - descuento
            return precioFinal.toInt().toString()
        }
        return precioOriginal
    }
    fun finalizarCompra(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _enviando.value = true
            val listaActual = _pasteles.value

            if (listaActual.isNotEmpty()) {
                val exito = repository.enviarPedidoInternet(listaActual)
                if (exito) {
                    repository.limpiarCarrito()
                    sincronizarNube()

                    Toast.makeText(getApplication(), "¡Pedido enviado con éxito!", Toast.LENGTH_LONG).show()
                    onSuccess()
                } else {
                    Toast.makeText(getApplication(), "Error al enviar pedido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(getApplication(), "El carrito está vacío", Toast.LENGTH_SHORT).show()
            }
            _enviando.value = false
        }
    }
}