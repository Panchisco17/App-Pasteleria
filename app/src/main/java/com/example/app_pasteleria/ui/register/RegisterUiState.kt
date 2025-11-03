package com.example.app_pasteleria.ui.register.data

data class RegisterUiState(
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val fechaNacimiento: String = "dd-mm-aaaa",
    val codigoDescuento: String = "",

    val isLoading: Boolean = false,
    val error: String? = null,
    val isCodeValid: Boolean = true,
    val descuento50Anios: Boolean = false,
    val descuentoFelices50: Boolean = false,
    val descuentoDuoc: Boolean = false
)