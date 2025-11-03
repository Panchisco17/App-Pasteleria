package com.example.app_pasteleria.ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_pasteleria.data.repository.AuthRepository
import com.example.app_pasteleria.ui.register.data.RegisterUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class RegisterViewModel(
    private val repo: AuthRepository = AuthRepository()
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

    fun onFechaNacimientoChange(value: String) {
        uiState = uiState.copy(fechaNacimiento = value, error = null)
    }

    private fun hoyCumple(fechaNacimiento: String): Boolean {
        // Renombramos 'formatter' a 'formateador'
        val formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return try {
            // Renombramos 'birthDate' a 'fechaNacimientoUsuario'
            val fechaNacimientoUsuario = LocalDate.parse(fechaNacimiento, formateador)
            // Renombramos 'currentDate' a 'fechaActual'
            val fechaActual = LocalDate.now()

            // La lógica de comparación se mantiene: Día y Mes del usuario vs. Día y Mes actual
            fechaNacimientoUsuario.dayOfMonth == fechaActual.dayOfMonth && fechaNacimientoUsuario.month == fechaActual.month
        } catch (e: DateTimeParseException) {
            false
        }
    }

    fun ocultarMensajeCumple() {
        uiState = uiState.copy(mensajeCumple = false)
    }

    fun submit(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null, mensajeCumple = false)

            if (uiState.nombre.isBlank() || uiState.apellido.isBlank() || uiState.email.isBlank() || uiState.password.isBlank() || uiState.fechaNacimiento.isBlank()) {
                uiState = uiState.copy(isLoading = false, error = "Complete todos los campos obligatorios.")
                return@launch
            }

            if (uiState.password.length < 3) {
                uiState = uiState.copy(isLoading = false, error = "La contraseña debe tener al menos 3 caracteres.")
                return@launch
            }

            val isDuocEmail = uiState.email.trim().endsWith("@duocuc.cl", ignoreCase = true)
            val isBirthday = hoyCumple(uiState.fechaNacimiento)

            val descuentoDuoc = isDuocEmail
            val descuento50Anios = false
            val descuentoFelices50 = false


            try {
                val username = uiState.email.trim()
                val registrationOk = repo.register(username, uiState.password)

                delay(1000)

                if (registrationOk) {
                    uiState = uiState.copy(
                        isLoading = false,
                        descuento50Anios = descuento50Anios,
                        descuentoFelices50 = descuentoFelices50,
                        descuentoDuoc = descuentoDuoc,
                        mensajeCumple = isDuocEmail && isBirthday,
                        error = null
                    )
                    onSuccess()
                } else {
                    uiState = uiState.copy(isLoading = false, error = "El correo ya está registrado.")
                }

            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = "Fallo el registro: ${e.message}")
            }
        }
    }
}