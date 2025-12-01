package com.example.app_pasteleria.viewmodel

import android.app.Application
import com.example.app_pasteleria.data.dao.CatalogoDao
import com.example.app_pasteleria.data.database.CatalogoDatabase
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatalogoViewModelTest {

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
    private fun createViewModel(): CatalogoViewModel {
        val mockApp = mockk<Application>(relaxed = true)
        val mockDb = mockk<CatalogoDatabase>()
        val mockDao = mockk<CatalogoDao>()

        every { CatalogoDatabase.getDatabase(any()) } returns mockDb
        every { mockDb.CatalogoDao() } returns mockDao

        every { mockDao.obtenerCatalogo() } returns flowOf(emptyList())

        return CatalogoViewModel(mockApp)
    }

    @Test
    fun `calcularPrecioFinal aplica 10 porciento de descuento con codigo FELICES50`() {
        val viewModel = createViewModel()

        val precioOriginal = "10000"
        val codigoDescuento = "FELICES50"

        val resultado = viewModel.calcularPrecioFinal(precioOriginal, codigoDescuento)

        assertEquals("9000", resultado)
    }

    @Test
    fun `calcularPrecioFinal NO aplica descuento con codigo incorrecto`() {
        val viewModel = createViewModel()

        val precioOriginal = "10000"
        val codigoIncorrecto = "CODIGO_INVALIDO"

        val resultado = viewModel.calcularPrecioFinal(precioOriginal, codigoIncorrecto)

        assertEquals("10000", resultado)
    }
}