package com.example.app_pasteleria.viewmodel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CatalogoViewModelTest {

    @Test
    fun `calcularPrecioFinal aplica 10 porciento de descuento con codigo FELICES50`() {
        // 1. Instanciar ViewModel (no requiere mocks complejos para esta función)
        val viewModel = CatalogoViewModel()

        // 2. Definir datos de prueba
        val precioOriginal = "10000"
        val codigoDescuento = "FELICES50"

        // 3. Ejecutar la lógica
        val resultado = viewModel.calcularPrecioFinal(precioOriginal, codigoDescuento)

        // 4. Verificar: El 10% de 10000 es 1000, entonces debe quedar en 9000
        assertEquals("9000", resultado)
    }

    @Test
    fun `calcularPrecioFinal NO aplica descuento con codigo incorrecto`() {
        val viewModel = CatalogoViewModel()

        val precioOriginal = "10000"
        val codigoIncorrecto = "CODIGO_INVALIDO"

        val resultado = viewModel.calcularPrecioFinal(precioOriginal, codigoIncorrecto)

        // Debe mantenerse el precio original
        assertEquals("10000", resultado)
    }
}