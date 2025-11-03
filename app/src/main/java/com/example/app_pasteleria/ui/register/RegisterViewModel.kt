package com.example.app_pasteleria.ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_pasteleria.data.repository.AuthRepository // Asumo esta importación
import com.example.app_pasteleria.ui.register.data.RegisterUiState // La clase que enviaste
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class RegisterViewModel(
    private val repo: AuthRepository = AuthRepository() // Reutilizando tu AuthRepository
) : ViewModel() {

    var uiState by mutableStateOf(RegisterUiState())


    fun onNombreChange(value: String) {
        uiState = uiState.copy(nombre = value, error = null)
    }

    fun onApellidoChange(value: String) {
        uiState = uiState.copy(apellido = value, error = null)
    }

    fun onEmailChange(value: String) {
        uiState = uiState.copy(email = value, error = null)
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value, error = null)
    }

    fun onConfirmPasswordChange(value: String) {
        uiState = uiState.copy(confirmPassword = value, error = null)
    }

    fun onFechaNacimientoChange(value: String) {
        uiState = uiState.copy(fechaNacimiento = value, error = null)
    }

    fun onCodigoDescuentoChange(value: String) {
        uiState = uiState.copy(codigoDescuento = value, error = null)
    }

    private fun calculateAge(dateOfBirth: String): Int {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        try {
            val birthDate = LocalDate.parse(dateOfBirth, formatter)
            val currentDate = LocalDate.now()
            return Period.between(birthDate, currentDate).years
        } catch (e: DateTimeParseException) {
            // Retorna 0 si el formato es incorrecto, para no aplicar el descuento
            return 0
        }
    }

    fun submit(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            // 1. Validaciones Preliminares
            if (uiState.password != uiState.confirmPassword) {
                uiState = uiState.copy(isLoading = false, error = "Las contraseñas no coinciden.")
                return@launch
            }

            if (uiState.nombre.isBlank() || uiState.email.isBlank() || uiState.password.isBlank()) {
                uiState = uiState.copy(isLoading = false, error = "Complete los campos obligatorios.")
                return@launch
            }
            val age = calculateAge(uiState.fechaNacimiento)
            val descuento50Anios = age >= 50
            val codigo = uiState.codigoDescuento.trim()
            val descuentoFelices50 = codigo.equals("felices50", ignoreCase = true)
            val descuentoDuoc = uiState.email.trim().endsWith("@duocuc.cl", ignoreCase = true)

            try {
                uiState = uiState.copy(
                    isLoading = false,
                    descuento50Anios = descuento50Anios,
                    descuentoFelices50 = descuentoFelices50,
                    descuentoDuoc = descuentoDuoc,
                    error = if (!codigo.isBlank() && !descuentoFelices50) "Código de descuento inválido" else null
                )

                onSuccess()

            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = "Fallo el registro: ${e.message}")
            }
        }
    }
}