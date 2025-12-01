package com.example.app_pasteleria.ui.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_cargaCorrectamente_elementosVisibles() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }

        composeTestRule.onNodeWithText("Inicio de sesión").assertIsDisplayed()

        composeTestRule.onNodeWithText("Correo Electrónico").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        composeTestRule.onNodeWithText("Iniciar Sesion").assertIsDisplayed()
    }

    @Test
    fun loginScreen_permiteEscribirUsuarioYContrasena() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }

        composeTestRule.onNodeWithText("Correo Electrónico")
            .performTextInput("prueba@duocuc.cl")

        composeTestRule.onNodeWithText("Contraseña")
            .performTextInput("123456")

        composeTestRule.onNodeWithText("Iniciar Sesion").performClick()
    }
}