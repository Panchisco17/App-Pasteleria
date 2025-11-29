package com.example.app_pasteleria.ui.register

import com.example.app_pasteleria.data.repository.AuthRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

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
    fun `submit debe fallar si la password tiene menos de 3 caracteres`() = runTest {
        // 1. Mock del repositorio (aunque no se debería llamar si falla la validación)
        val mockRepo = mockk<AuthRepository>()
        val viewModel = RegisterViewModel(repo = mockRepo)

        // 2. Llenar el formulario con datos, pero password corta
        viewModel.onNombreChange("Juan")
        viewModel.onApellidoChange("Perez")
        viewModel.onEmailChange("juan@test.cl")
        viewModel.onFechaNacimientoChange("01-01-1990")
        viewModel.onPasswordChange("12") // <--- Contraseña inválida (2 chars)

        // 3. Ejecutar submit
        viewModel.submit {}

        // 4. Verificar el error específico definido en tu RegisterViewModel
        assertEquals("La contraseña debe tener al menos 3 caracteres.", viewModel.uiState.error)
    }
}