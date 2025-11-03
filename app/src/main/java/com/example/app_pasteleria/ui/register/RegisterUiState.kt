package com.example.app_pasteleria.ui.register.data

import java.time.LocalDate

data class RegisterUiState(
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val password: String = "",
    val fechaNacimiento: String = "",

    val mensajeCumple: Boolean = false,

    val isLoading: Boolean = false,
    val error: String? = null,

    val descuento50Anios: Boolean = false,
    val descuentoFelices50: Boolean = false,
    val descuentoDuoc: Boolean = false
)