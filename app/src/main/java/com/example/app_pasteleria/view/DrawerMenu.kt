package com.example.app_pasteleria.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app_pasteleria.R
import com.example.app_pasteleria.viewmodel.PostreViewModel

// Función auxiliar para las imágenes (copiada para que funcione aquí)
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

@Composable
fun DrawerMenu(
    username: String,
    navController: NavController,
    // Inyectamos el ViewModel que ya tiene la lógica de la API
    postreViewModel: PostreViewModel = viewModel()
){
    // Observamos la lista que viene de la API (definida en PostreViewModel.kt)
    val listaPastelesApi by postreViewModel.postList.collectAsState()

    val separador = Color(0xFF623608)
    val itemTextColor = Color(0xFF623608)
    val itemIconSize = 30.dp
    val itemTextSize = 17.sp
    val itemColors = NavigationDrawerItemDefaults.colors(
        unselectedTextColor = itemTextColor,
        unselectedIconColor = itemTextColor
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFF7C460D))
        ) {
            Text(
                text = "Catálogo Online",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Cursive
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        LazyColumn(modifier = Modifier
            .weight(1f)
            .background(Color(0xFFEAD3AC))
        ){
            // Aquí ocurre la magia: Iteramos sobre la lista de la API
            if (listaPastelesApi.isEmpty()) {
                item {
                    Text(
                        text = "Cargando productos...",
                        modifier = Modifier.padding(16.dp),
                        color = itemTextColor
                    )
                }
            } else {
                items(listaPastelesApi) { postre ->
                    val imageId = getProductoImagenId(postre.nombre)

                    NavigationDrawerItem(
                        label = {
                            Column {
                                Text(postre.nombre, fontSize = itemTextSize)
                                Text("$${postre.precio}", fontSize = 14.sp, color = Color.Gray)
                            }
                        },
                        selected = false,
                        onClick = {
                            // Navegación dinámica usando los datos de la API
                            val nombreCodificado = Uri.encode(postre.nombre)
                            navController.navigate("CatalogoFormScreen/$nombreCodificado/${postre.precio}")
                        },
                        icon = {
                            // Usamos la imagen real en lugar del icono genérico si lo prefieres
                            Image(
                                painter = painterResource(id = imageId),
                                contentDescription = postre.nombre,
                                modifier = Modifier.size(itemIconSize)
                            )
                        },
                        colors = itemColors,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Divider(color = separador, thickness = 1.dp)
                }
            }
        }

        // Footer
        Text(
            text = "@ 2025 Pasteleria Mil Sabores\nUsuario: $username",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFCEB487))
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}