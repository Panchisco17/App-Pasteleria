package com.example.app_pasteleria.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.app_pasteleria.data.model.Catalogo
import com.example.app_pasteleria.viewmodel.CatalogoViewModel
import com.example.app_pasteleria.R

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
    nombre:String,
    precio: String
) {// Inicio

    var cantidad by remember { mutableStateOf(TextFieldValue("")) }
    var descuentoInput by remember { mutableStateOf(TextFieldValue("")) }
    var aplicarDescuento by remember { mutableStateOf(false) }

    val viewModel: CatalogoViewModel = viewModel()
    val catalogos: List<Catalogo> by viewModel.pasteles.collectAsState()
    val imageId = getProductoImagenId(nombre)
    val topBarColor = Color(0xFF7C460D)
    val topBarContentColor = MaterialTheme.colorScheme.onPrimary

    val precioFinalString = remember(descuentoInput.text, aplicarDescuento) {
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
    MaterialTheme(
        colorScheme = ColorScheme
    ) {

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
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.background

        ) // fin Scaffold


        {// inicio inner
                innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Inicio Contenido

                    Spacer(modifier = Modifier.height(16.dp))


                    Image(
                        painter = painterResource(id = imageId),
                        contentDescription = "Imagen Producto",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (precio == precioFinalString) {
                            Arrangement.Center
                        } else {
                            Arrangement.Center
                        }
                    ) {
                        Text(
                            text = "Precio: $$precio",
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (precio != precioFinalString) Color.Gray else MaterialTheme.colorScheme.primary,
                        )

                        if (precio != precioFinalString) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Precio final: $$precioFinalString",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = cantidad,
                        onValueChange = { cantidad = it },
                        label = {
                            Text(
                                "Cantidad",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

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

                    if (aplicarDescuento) {
                        OutlinedTextField(
                            value = descuentoInput,
                            onValueChange = { descuentoInput = it },
                            label = {
                                Text(
                                    "Código de descuento",
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val isButtonEnabled = if (aplicarDescuento) {
                        cantidad.text.isNotBlank() && descuentoInput.text.isNotBlank()
                    } else {
                        cantidad.text.isNotBlank()
                    }

                    Button(
                        onClick = {
                            val precioAGuardar = viewModel.calcularPrecioFinal(
                                precioOriginal = precio,
                                codigoDescuento = if (aplicarDescuento) descuentoInput.text else null
                            )

                            val catalogo = Catalogo(
                                nombre = nombre,
                                precio = precioAGuardar,
                                cantidad = cantidad.text
                            )
                            viewModel.guardarPastel(catalogo)

                        },
                        enabled = isButtonEnabled
                    )
                    {
                        Text("Agregar al carrito", color = MaterialTheme.colorScheme.onPrimary)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // mostrar los productos guardados
                    Text(
                        "Productos agregados: ",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    if (catalogos.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(catalogos) { catalogo ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                )
                                {
                                    Text(
                                        text = "${catalogo.nombre} - $${catalogo.precio}", // Muestra el precio guardado
                                        style = MaterialTheme.typography.bodyLarge
                                    )

                                    Text(
                                        text = "Cantidad: ${catalogo.cantidad}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                    } else {
                        Text(
                            "No hay productos agregados",
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                } //Fin Contenido

                // footer
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

            } // fin inner
        }//fin
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