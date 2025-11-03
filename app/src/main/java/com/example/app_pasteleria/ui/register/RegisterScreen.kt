package com.example.app_pasteleria.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    vm: RegisterViewModel = viewModel()
) {
    val state = vm.uiState

    var showPass by remember { mutableStateOf(false) }
    var showConfirmPass by remember { mutableStateOf(false) }

    val primaryColor = Color(0xFF623608)
    val secondaryColor = Color(0xFF7C460D)
    val surfaceColor = Color(0xFFEAD3AC)
    val onSurfaceColor = Color(0xFFB9863C)

    Scaffold(
        containerColor = surfaceColor,
        topBar = {
            TopAppBar(title = {
                Text(
                    "Crear Nueva Cuenta",
                    color = primaryColor,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {

                item {
                    Text(
                        text = "Registro de Usuario",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 35.sp,
                            fontFamily = FontFamily.Cursive
                        ),
                        color = primaryColor,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                item { FormLabel("Nombre") }
                item { OutlinedTextField(value = state.nombre, onValueChange = vm::onNombreChange, // VINCULADO
                    label = { Text("Ingrese su nombre...") }, modifier = Modifier.fillMaxWidth())
                }

                item { FormLabel("Apellido") }
                item { OutlinedTextField(value = state.apellido, onValueChange = vm::onApellidoChange, // VINCULADO
                    label = { Text("Ingrese su apellido...") }, modifier = Modifier.fillMaxWidth())
                }

                item { FormLabel("Correo Electrónico") }
                item { OutlinedTextField(value = state.email, onValueChange = vm::onEmailChange, // VINCULADO
                    label = { Text("ejemplo@duocuc.cl") }, modifier = Modifier.fillMaxWidth())
                }

                item { FormLabel("Contraseña") }
                item {
                    OutlinedTextField(value = state.password, onValueChange = vm::onPasswordChange, // VINCULADO
                        visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = { TextButton(onClick = { showPass = !showPass }) { Text(if (showPass) "Ocultar" else "Ver") } },
                        modifier = Modifier.fillMaxWidth())
                }
                item {
                    Text(
                        "Tu contraseña debe tener entre 8 y 20 caracteres, contener letras y números, y no debe tener espacios o caracteres especiales.",
                        style = MaterialTheme.typography.bodySmall,
                        color = secondaryColor.copy(alpha = 0.8f),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item { FormLabel("Confirma tu contraseña") }
                item {
                    OutlinedTextField(value = state.confirmPassword, onValueChange = vm::onConfirmPasswordChange, // VINCULADO
                        visualTransformation = if (showConfirmPass) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = { TextButton(onClick = { showConfirmPass = !showConfirmPass }) { Text(if (showConfirmPass) "Ocultar" else "Ver") } },
                        modifier = Modifier.fillMaxWidth())
                }

                item { FormLabel("Fecha de Nacimiento") }
                item {
                    OutlinedTextField(
                        value = state.fechaNacimiento,
                        onValueChange = vm::onFechaNacimientoChange, // VINCULADO
                        label = { Text("dd-mm-aaaa") },
                        trailingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = "Calendario") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item { FormLabel("Código de descuento (Opcional)") }
                item { OutlinedTextField(value = state.codigoDescuento, onValueChange = vm::onCodigoDescuentoChange, // VINCULADO
                    label = { Text("Ingrese su código...") }, modifier = Modifier.fillMaxWidth())
                }

                if (state.error != null) {
                    item {
                        Text(state.error!!, color = Color.Red, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    }
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }

            } // Fin LazyColumn

            Button(
                onClick = {
                    vm.submit {
                        navController.navigate("login")
                    }
                },
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(if(state.isLoading) "Registrando..." else "Registrarse",
                    color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            TextButton(onClick = { navController.navigate("login") }) {
                Text("¿Ya tienes cuenta? Inicia Sesión")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FormLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}