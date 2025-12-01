package com.example.app_pasteleria.ui.register

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_pasteleria.data.database.CatalogoDatabase
import com.example.app_pasteleria.data.model.Usuario
import com.example.app_pasteleria.data.repository.AuthRepository
import com.example.app_pasteleria.ui.register.data.RegisterUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AuthRepository

    init {
        val db = CatalogoDatabase.getDatabase(application)
        repo = AuthRepository(db.UsuarioDao())
    }

    var uiState by androidx.compose.runtime.mutableStateOf(RegisterUiState())
        private set

    fun onNombreChange(value: String) { uiState = uiState.copy(nombre = value, error = null) }
    fun onApellidoChange(value: String) { uiState = uiState.copy(apellido = value, error = null) }
    fun onEmailChange(value: String) { uiState = uiState.copy(email = value, error = null) }
    fun onPasswordChange(value: String) { uiState = uiState.copy(password = value, error = null) }
    fun onFechaNacimientoChange(value: String) { uiState = uiState.copy(fechaNacimiento = value, error = null) }

    fun ocultarMensajeCumple() { uiState = uiState.copy(mensajeCumple = false) }

    private fun hoyCumple(fechaNacimiento: String): Boolean {
        val formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return try {
            val fechaNacimientoUsuario = LocalDate.parse(fechaNacimiento, formateador)
            val fechaActual = LocalDate.now()
            fechaNacimientoUsuario.dayOfMonth == fechaActual.dayOfMonth && fechaNacimientoUsuario.month == fechaActual.month
        } catch (e: DateTimeParseException) {
            false
        }
    }

    fun submit(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null, mensajeCumple = false)

            if (uiState.nombre.isBlank() || uiState.apellido.isBlank() || uiState.email.isBlank() || uiState.password.isBlank() || uiState.fechaNacimiento.isBlank()) {
                uiState = uiState.copy(isLoading = false, error = "Complete todos los campos.")
                return@launch
            }
            if (uiState.password.length < 3) {
                uiState = uiState.copy(isLoading = false, error = "La contraseña debe tener al menos 3 caracteres.")
                return@launch
            }

            val isDuocEmail = uiState.email.trim().endsWith("@duocuc.cl", ignoreCase = true)
            val isBirthday = hoyCumple(uiState.fechaNacimiento)

            val nuevoUsuario = Usuario(
                nombre = uiState.nombre,
                apellido = uiState.apellido,
                email = uiState.email.trim(),
                password = uiState.password,
                fechaNacimiento = uiState.fechaNacimiento
            )

            try {
                val registrationOk = repo.register(nuevoUsuario)

                delay(1000) // Simular espera visual

                if (registrationOk) {
                    uiState = uiState.copy(
                        isLoading = false,
                        descuentoDuoc = isDuocEmail,
                        mensajeCumple = isDuocEmail && isBirthday,
                        error = null
                    )
                    onSuccess()
                } else {
                    uiState = uiState.copy(isLoading = false, error = "El correo ya está registrado.")
                }

            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = "Fallo: ${e.message}")
            }
        }
    }
}