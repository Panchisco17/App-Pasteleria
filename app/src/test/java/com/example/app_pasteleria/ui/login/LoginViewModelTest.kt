package com.example.app_pasteleria.ui.login

import com.example.app_pasteleria.data.repository.AuthRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `si el login falla debe actualizar el estado con error`() {
        // 1. Mockear el repositorio
        val mockRepo = mockk<AuthRepository>()
        // Definimos que cualquier intento de login retorne falso (fallido)
        every { mockRepo.login(any(), any()) } returns false

        // 2. Crear el ViewModel inyectando el mock
        val viewModel = LoginViewModel(repo = mockRepo)

        // 3. Simular entrada de datos del usuario
        viewModel.onUsernameChange("usuario@error.cl")
        viewModel.onPasswordChange("123456")

        // 4. Ejecutar el submit
        viewModel.submit { /* No nos importa el éxito aquí */ }

        // 5. Verificar que el estado tenga el mensaje de error esperado
        assertEquals("Credenciales Invalidas", viewModel.uiState.error)
    }
}