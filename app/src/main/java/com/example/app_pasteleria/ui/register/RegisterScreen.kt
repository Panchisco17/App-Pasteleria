package com.example.app_pasteleria.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    vm: RegisterViewModel= viewModel()

){
    val state = vm.uiState
    var showPass by remember { mutableStateOf(false) }
    var aceptarTerminos by remember { mutableStateOf(false) }

    var showDatePickerDialog by remember { mutableStateOf(false) }
    val estadoSelectorFecha = rememberDatePickerState()
    val snackbarHostState = remember { SnackbarHostState() }

    fun convertirFecha(millis: Long): String {
        val formateo = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return formateo.format(Date(millis))
    }

    val ColorScheme = darkColorScheme(
        primary= Color(0xFF623608),
        secondary = Color(0xFF7C460D),
        onSurface = Color(0xFFB9863C),
        surface = Color(0xFFEAD3AC),
        background = Color(0xFFEAD3AC),
        onPrimary = Color.White,

        )

    MaterialTheme(
        colorScheme = ColorScheme
    ){
        Scaffold (

            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(title = {Text("Pasteleria Mil Sabores",
                    color =MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    )})
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        )
        { innerPadding ->

            Column (
                modifier = Modifier
                    .padding( innerPadding)
                    .fillMaxSize()
                    .padding(16.dp, vertical = 12.dp)
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text="Registro de usuarios",
                    style= MaterialTheme.typography.headlineMedium,
                    color=MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 35.sp

                )

                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text("Ingrese su Nombre",
                        style =MaterialTheme.typography.bodyLarge.copy(
                            color=MaterialTheme.colorScheme.secondary.copy(alpha=0.8f),
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(end=8.dp)
                    )
                }

                OutlinedTextField(
                    value = state.nombre,
                    onValueChange = vm::onNombreChange,
                    label ={Text("Nombre...", color = MaterialTheme.colorScheme.onSurface)},
                    singleLine = true ,
                    modifier = Modifier.fillMaxWidth(0.95f),

                    )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text("Ingrese su Apellido",
                        style =MaterialTheme.typography.bodyLarge.copy(
                            color=MaterialTheme.colorScheme.secondary.copy(alpha=0.8f),
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(end=8.dp)
                    )
                }

                OutlinedTextField(
                    value = state.apellido,
                    onValueChange = vm::onApellidoChange,
                    label ={Text("Apelliso...", color = MaterialTheme.colorScheme.onSurface)},
                    singleLine = true ,
                    modifier = Modifier.fillMaxWidth(0.95f),

                    )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text("Fecha de Nacimiento",
                        style =MaterialTheme.typography.bodyLarge.copy(
                            color=MaterialTheme.colorScheme.secondary.copy(alpha=0.8f),
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(end=8.dp)
                    )
                }

                OutlinedTextField(
                    value = state.fechaNacimiento,
                    onValueChange = {  },
                    readOnly = true,
                    label ={Text("dd-MM-yyyy", color = MaterialTheme.colorScheme.onSurface)},
                    singleLine = true ,
                    modifier = Modifier.fillMaxWidth(0.95f),
                    trailingIcon = {
                        IconButton(onClick = { showDatePickerDialog = true }) {
                            Icon(Icons.Filled.DateRange, contentDescription = "Seleccionar Fecha")
                        }
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text("Ingrese su correo electrónico",
                        style =MaterialTheme.typography.bodyLarge.copy(
                            color=MaterialTheme.colorScheme.secondary.copy(alpha=0.8f),
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(end=8.dp)
                    )
                }

                OutlinedTextField(
                    value = state.email,
                    onValueChange = vm::onEmailChange,
                    label ={Text("example@duocuc.cl", color = MaterialTheme.colorScheme.onSurface)},
                    singleLine = true ,
                    modifier = Modifier.fillMaxWidth(0.95f),

                    )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text("Ingrese su contraseña",
                        style =MaterialTheme.typography.bodyLarge.copy(
                            color=MaterialTheme.colorScheme.secondary.copy(alpha=0.8f),
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(end=8.dp)
                    )
                }

                OutlinedTextField(
                    value = state.password,
                    onValueChange = vm::onPasswordChange,
                    label ={Text("Contraseña", color = MaterialTheme.colorScheme.onSurface)},
                    singleLine = true ,
                    visualTransformation = if (showPass) VisualTransformation.None else
                        PasswordVisualTransformation(),

                    trailingIcon = {
                        TextButton(onClick = {showPass =!showPass})
                        {
                            Text(if (showPass)"Ocultar" else "Ver")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.95f)
                )

                if (state.error != null){
                    Spacer (Modifier.height(8.dp))
                    Text(
                        text = state.error?:"",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .padding(top = 8.dp, bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = aceptarTerminos,
                        onCheckedChange = { isChecked -> aceptarTerminos = isChecked },

                        )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Acepto los términos y condiciones",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Button(
                    onClick = {
                        vm.submit {
                            navController.navigate(route = "login") {
                                popUpTo("register") { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    },
                    enabled = !state.isLoading && aceptarTerminos,
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text(if (state.isLoading) "Validando" else "Registrarse")
                }
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ){
                    Text("¿Ya tienes cuenta?",
                        style =MaterialTheme.typography.bodyLarge.copy(
                            color=MaterialTheme.colorScheme.secondary.copy(alpha=0.8f),
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier
                    )
                }

                Button(
                    onClick = {
                        navController.navigate("login")
                    },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text("Iniciar Sesión")
                }
            }

            if (showDatePickerDialog) {
                DatePickerDialog(
                    onDismissRequest = { showDatePickerDialog = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                estadoSelectorFecha.selectedDateMillis?.let { millis ->
                                    val fechaSeleccionada = convertirFecha(millis)
                                    vm.onFechaNacimientoChange(fechaSeleccionada)
                                }
                                showDatePickerDialog = false
                            },
                            enabled = estadoSelectorFecha.selectedDateMillis != null
                        ) {
                            Text("Aceptar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePickerDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                ) {
                    DatePicker(state = estadoSelectorFecha)
                }
            }

            if (state.mensajeCumple) {
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(
                        message = "¡Felicidades! Por tu cumpleaños tienes un pastel GRATIS por ser DUOC.CL.",
                        actionLabel = "Aceptar"
                    )
                    vm.ocultarMensajeCumple()
                }
            }

        } // Fin inner


    } // fin Aplicar Material
}// Fin RegisterScreen


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview(){
    val navController = rememberNavController()
    val vm = RegisterViewModel()

    RegisterScreen(navController = navController, vm = vm)
}// Fin RegisterScreen