package com.example.app_pasteleria.view

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_pasteleria.R
import com.example.app_pasteleria.data.model.Catalogo
import com.example.app_pasteleria.utils.CameraPermissionHelper
import com.example.app_pasteleria.viewmodel.CatalogoViewModel
import com.example.app_pasteleria.viewmodel.QrViewModel

@Composable
private fun getProductoImagenId(nombre: String): Int {
    return when (nombre) {
        "Torta Cuadrada de Chocolate" -> R.drawable.torta_cuadrada_chocolate
        "Torta Cuadrada de Frutas" -> R.drawable.torta_cuadrada_frutas
        "Torta circular de manjar" -> R.drawable.torta_circular_manjar
        "Torta circular de vainilla" -> R.drawable.torta_vainilla
        "Tiramisú clásico" -> R.drawable.tiramisu
        "Mousse de chocolate" -> R.drawable.mousse
        "Torta de naranja sin azúcar" -> R.drawable.torta_naranja
        "Cheesecake sin azúcar" -> R.drawable.cheesecake
        "Empanada de mánzana" -> R.drawable.empanada_manzana
        "Tarta Santiago" -> R.drawable.tarta_santiago
        "Brownie sin glúten" -> R.drawable.brownie
        "Pan sin glúten" -> R.drawable.pan
        "Torta vegana de chocolate" -> R.drawable.torta_vegana
        "Galletas veganas" -> R.drawable.galletas_veganas
        "Torta de Boda" -> R.drawable.torta_boda
        "Torta de cumpleaños" -> R.drawable.torta_cumple
        else -> R.drawable.logo
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoFormScreen(
    navController: NavController,
    nombre: String,
    precio: String
) {
    var cantidad by remember { mutableStateOf(TextFieldValue("")) }
    var descuentoInput by remember { mutableStateOf(TextFieldValue("")) }
    var aplicarDescuento by remember { mutableStateOf(false) }

    var showQrScanner by remember { mutableStateOf(false) }
    val qrViewModel: QrViewModel = viewModel()
    val qrResult by qrViewModel.qrResult.observeAsState()
    val context = LocalContext.current

    var hasCameraPermission by remember {
        mutableStateOf(CameraPermissionHelper.hasCameraPermission(context))
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
        if (isGranted) {
            showQrScanner = true
        }
    }

    LaunchedEffect(qrResult) {
        qrResult?.let { result ->
            descuentoInput = TextFieldValue(result.content)
            aplicarDescuento = true
            showQrScanner = false
            qrViewModel.clearResult()
        }
    }

    val viewModel: CatalogoViewModel = viewModel()
    val catalogos: List<Catalogo> by viewModel.pasteles.collectAsState()

    val imageId = getProductoImagenId(nombre)
    val topBarColor = Color(0xFF7C460D)
    val topBarContentColor = MaterialTheme.colorScheme.onPrimary

    val precioFinalUnitarioString = remember(descuentoInput.text, aplicarDescuento) {
        if (aplicarDescuento) {
            viewModel.calcularPrecioFinal(precio, descuentoInput.text)
        } else {
            precio
        }
    }

    val ColorScheme = darkColorScheme(
        primary = Color(0xFF623608),
        secondary = Color(0xFF7C460D),
        onSurface = Color(0xFFB9863C),
        surface = Color(0xFFEAD3AC),
        background = Color(0xFFEAD3AC),
        onPrimary = Color.White,
        onSecondary = Color.Black
    )

    MaterialTheme(colorScheme = ColorScheme) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = topBarColor,
                        titleContentColor = topBarContentColor,
                    ),
                    title = {
                        Text(
                            text = "Confirmar Pedido",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontSize = 40.sp,
                                fontFamily = FontFamily.Cursive
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver",
                                tint = topBarContentColor
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("CarritoScreen") }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Ir al Carrito",
                                tint = topBarContentColor
                            )
                        }
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.background

        ) { innerPadding ->

            if (showQrScanner) {
                QrScannerScreen(
                    viewModel = qrViewModel,
                    hasCameraPermission = hasCameraPermission,
                    onRequestPermission = {
                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                    },
                    onClose = { showQrScanner = false },
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item { Spacer(modifier = Modifier.height(16.dp)) }

                        item {
                            Image(
                                painter = painterResource(id = imageId),
                                contentDescription = "Imagen Producto",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                            )
                        }

                        item { Spacer(modifier = Modifier.height(16.dp)) }

                        item {
                            Text(
                                text = nombre,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Precio: $$precio",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = if (precio != precioFinalUnitarioString) Color.Gray else MaterialTheme.colorScheme.primary,
                                )

                                if (precio != precioFinalUnitarioString) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Precio final: $$precioFinalUnitarioString",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        item { Spacer(modifier = Modifier.height(16.dp)) }

                        item { //cantidad
                            OutlinedTextField(
                                value = cantidad,
                                onValueChange = { cantidad = it },
                                label = { Text("Cantidad", color = MaterialTheme.colorScheme.onSurface) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        } // fin item cantidad


                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { aplicarDescuento = !aplicarDescuento }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = aplicarDescuento,
                                    onCheckedChange = {
                                        aplicarDescuento = it
                                        if (!it) {
                                            descuentoInput = TextFieldValue("")
                                        }
                                    }
                                )
                                Text(
                                    text = "Aplicar Código de Descuento",
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }

                        if (aplicarDescuento) {
                            item {
                                OutlinedTextField(
                                    value = descuentoInput,
                                    onValueChange = { descuentoInput = it },
                                    label = { Text("Código de descuento", color = MaterialTheme.colorScheme.onSurface) },
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            if (hasCameraPermission) {
                                                showQrScanner = true
                                            } else {
                                                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                                            }
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.QrCodeScanner,
                                                contentDescription = "Escanear Código QR",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            item { Spacer(modifier = Modifier.height(16.dp)) }
                        }

                        item { Spacer(modifier = Modifier.height(16.dp)) }

                        item {
                            val isButtonEnabled = if (aplicarDescuento) {
                                cantidad.text.isNotBlank() && descuentoInput.text.isNotBlank()
                            } else {
                                cantidad.text.isNotBlank()
                            }

                            Button(
                                onClick = {
                                    val precioUnitario = precioFinalUnitarioString.toDoubleOrNull() ?: 0.0
                                    val cantidadInt = cantidad.text.toIntOrNull() ?: 1
                                    val precioTotalAGuardar = (precioUnitario * cantidadInt).toString()

                                    val catalogo = Catalogo(
                                        nombre = nombre,
                                        precio = precioTotalAGuardar,
                                        cantidad = cantidad.text

                                    )
                                    viewModel.guardarPastel(catalogo)


                                },
                                enabled = isButtonEnabled
                            )
                            {
                                Text("Agregar al carrito", color = MaterialTheme.colorScheme.onPrimary)
                            }
                        }

                        item { Spacer(modifier = Modifier.height(16.dp)) }

                        item {
                            Text(
                                "Productos agregados: ",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        if (catalogos.isNotEmpty()) {
                            items(catalogos) { catalogo ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                )
                                {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            text = "${catalogo.nombre} - $${catalogo.precio}",
                                            style = MaterialTheme.typography.bodyLarge
                                        ) // fin text nombre y precio
                                        Text(
                                            text = "Cantidad: ${catalogo.cantidad}",
                                            style = MaterialTheme.typography.bodyMedium
                                        ) // fin text cantidad


                                    }
                                }
                            }
                        } else {
                            item {
                                Text(
                                    "No hay productos agregados",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                    }

                    Text(
                        text = "@ 2025 Pasteleria Mil Sabores",
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFCEB487))
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductoFormScreen() {
    CatalogoFormScreen(
        navController = rememberNavController(),
        nombre = "Torta Cuadrada de Chocolate",
        precio = "45000"
    )
}