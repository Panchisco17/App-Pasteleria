package com.example.app_pasteleria.ui.register

import android.app.Application
import com.example.app_pasteleria.data.dao.UsuarioDao
import com.example.app_pasteleria.data.database.CatalogoDatabase
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
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
        mockkObject(CatalogoDatabase)
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(CatalogoDatabase)
        Dispatchers.resetMain()
    }

    @Test
    fun `submit debe fallar si la password tiene menos de 3 caracteres`() = runTest {
        val mockApp = mockk<Application>(relaxed = true)
        val mockDb = mockk<CatalogoDatabase>()
        val mockDao = mockk<UsuarioDao>()

        every { CatalogoDatabase.getDatabase(any()) } returns mockDb
        every { mockDb.UsuarioDao() } returns mockDao

        val viewModel = RegisterViewModel(mockApp)

        viewModel.onNombreChange("Juan")
        viewModel.onApellidoChange("Perez")
        viewModel.onEmailChange("juan@test.cl")
        viewModel.onFechaNacimientoChange("01-01-1990")
        viewModel.onPasswordChange("12") // <--- El problema (menos de 3 chars)
        viewModel.submit {}
        assertEquals("La contraseña debe tener al menos 3 caracteres.", viewModel.uiState.error)
    }
}