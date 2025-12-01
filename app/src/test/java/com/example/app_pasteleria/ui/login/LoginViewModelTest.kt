package com.example.app_pasteleria.ui.login

import android.app.Application
import com.example.app_pasteleria.data.dao.UsuarioDao
import com.example.app_pasteleria.data.database.CatalogoDatabase
import io.mockk.coEvery
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
class LoginViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        // Mockeamos el objeto compañero (Companion Object) de la base de datos
        mockkObject(CatalogoDatabase)
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(CatalogoDatabase)
        Dispatchers.resetMain()
    }

    @Test
    fun `si el login falla debe actualizar el estado con error`() = runTest {
        val mockApp = mockk<Application>(relaxed = true)
        val mockDb = mockk<CatalogoDatabase>()
        val mockDao = mockk<UsuarioDao>()

        every { CatalogoDatabase.getDatabase(any()) } returns mockDb
        every { mockDb.UsuarioDao() } returns mockDao
        coEvery { mockDao.login(any(), any()) } returns null

        val viewModel = LoginViewModel(mockApp)

        viewModel.onUsernameChange("usuario@error.cl")
        viewModel.onPasswordChange("123456")
        viewModel.submit { }
        assertEquals("Credenciales Invalidas", viewModel.uiState.error)
    }
}