package com.example.app_pasteleria.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp // Icono Salir
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app_pasteleria.viewmodel.PostreViewModel

private fun getProductoIcon(nombre: String): ImageVector {
    return when {
        nombre.contains("Galleta", ignoreCase = true) -> Icons.Default.Cookie
        nombre.contains("Torta", ignoreCase = true) -> Icons.Default.Cake
        nombre.contains("Cheesecake", ignoreCase = true) -> Icons.Default.Cake
        else -> Icons.Default.BakeryDining
    }
}

@Composable
fun DrawerMenu(
    username: String,
    navController: NavController,
    postreViewModel: PostreViewModel = viewModel()
){
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
            .weight(1f) // Esto hace que la lista ocupe todo el espacio disponible
            .background(Color(0xFFEAD3AC))
        ){
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { navController.navigate("CarritoScreen") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7C460D)
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "VER MI CARRITO",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                Divider(color = separador, thickness = 2.dp)
            }

            if (listaPastelesApi.isEmpty()) {
                item {
                    Text(
                        text = "Cargando productos...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = itemTextColor,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(listaPastelesApi) { postre ->
                    val iconVector = getProductoIcon(postre.nombre)

                    NavigationDrawerItem(
                        label = {
                            Column {
                                Text(postre.nombre, fontSize = itemTextSize)
                                Text(
                                    text = "$${postre.precio}",
                                    fontSize = 14.sp,
                                    color = Color.DarkGray
                                )
                            }
                        },
                        selected = false,
                        onClick = {
                            val nombreCodificado = Uri.encode(postre.nombre)
                            navController.navigate("CatalogoFormScreen/$nombreCodificado/${postre.precio}")
                        },
                        icon = {
                            Icon(
                                imageVector = iconVector,
                                contentDescription = postre.nombre,
                                modifier = Modifier.size(itemIconSize),
                                tint = itemTextColor
                            )
                        },
                        colors = itemColors
                    )
                    Divider(color = separador, thickness = 1.dp)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEAD3AC))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true } // Borra todo el historial
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF623608) // Un café más oscuro para diferenciarlo
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Salir",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar Sesión")
            }
        }

        Text(
            text = "@ 2025 Pasteleria Mil Sabores\nUsuario: $username",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFCEB487))
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}