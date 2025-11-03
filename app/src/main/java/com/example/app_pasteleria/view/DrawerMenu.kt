package com.example.app_pasteleria.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable

fun DrawerMenu(
    username: String,
    navController: NavController
){ //inicio

    val separador = Color(0xFF623608)
    val itemTextColor = Color(0xFF623608)
    val itemIconSize = 30.dp
    val itemTextSize = 17.sp
    val itemColors = NavigationDrawerItemDefaults.colors(
        unselectedTextColor = itemTextColor,
        unselectedIconColor = itemTextColor
    )

    Column(modifier = Modifier.fillMaxSize())
    { // inicio columna
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFF7C460D))
        ) // fin box
        { // inicio contenido
            Text(
                text = "Catálogo de productos",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Cursive
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        } // fin contenido

        // LazyColumn: crear lista de elementos que se pueden desplazar verticalmente
        LazyColumn(modifier = Modifier
            .weight(1f)
            .background(Color(0xFFEAD3AC))
        ){

            item { // inicio item 1
                NavigationDrawerItem(
                    label = { Text("Torta Cuadrada de Chocolate", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Torta Cuadrada de Chocolate")
                        val precio = "45000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.Cake , contentDescription = "Torta Chocolate", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 1

            item { // inicio item 2
                NavigationDrawerItem(
                    label = { Text("Torta Cuadrada de Frutas", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Torta Cuadrada de Frutas")
                        val precio = "50000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.Cake , contentDescription = "Torta Frutas", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 2

            item { // inicio item 3
                NavigationDrawerItem(
                    label = { Text("Torta circular de manjar", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Torta circular de manjar")
                        val precio = "42000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.Cake , contentDescription = "Torta Manjar", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 3

            item { // inicio item 4
                NavigationDrawerItem(
                    label = { Text("Torta circular de vainilla", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Torta circular de vainilla")
                        val precio = "40000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.Cake , contentDescription = "Torta vainilla", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 4

            item { // inicio item 5
                NavigationDrawerItem(
                    label = { Text("Tiramisú clásico", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Tiramisú clásico")
                        val precio = "5500"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.BakeryDining , contentDescription = "Tiramisú clásico", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 5

            item { // inicio item 6
                NavigationDrawerItem(
                    label = { Text("Mousse de chocolate", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Mousse de chocolate")
                        val precio = "5000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.BakeryDining , contentDescription = "Mousse chocolate", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 6

            item { // inicio item 7
                NavigationDrawerItem(
                    label = { Text("Torta de naranja sin azúcar", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Torta de naranja sin azúcar")
                        val precio = "48000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.Cake , contentDescription = "Torta de naranja", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 7

            item { // inicio item 8
                NavigationDrawerItem(
                    label = { Text("Cheesecake sin azúcar", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Cheesecake sin azúcar")
                        val precio = "47000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.Cake , contentDescription = "Cheesecake", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 8

            item { // inicio item 9
                NavigationDrawerItem(
                    label = { Text("Empanada de mánzana", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Empanada de mánzana")
                        val precio = "3000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.BakeryDining , contentDescription = "Empanada mánzana", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 9

            item { // inicio item 10
                NavigationDrawerItem(
                    label = { Text("Tarta Santiago", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Tarta Santiago")
                        val precio = "3000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.BakeryDining , contentDescription = "Tarta Santiago", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 10

            item { // inicio item 11
                NavigationDrawerItem(
                    label = { Text("Brownie sin glúten", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Brownie sin glúten")
                        val precio = "4000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.BakeryDining , contentDescription = "Brownie", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 11

            item { // inicio item 12
                NavigationDrawerItem(
                    label = { Text("Pan sin glúten", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Pan sin glúten")
                        val precio = "3500"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.BakeryDining , contentDescription = "Pan", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 12

            item { // inicio item 13
                NavigationDrawerItem(
                    label = { Text("Torta vegana de chocolate", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Torta vegana de chocolate")
                        val precio = "50000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.Cake , contentDescription = "Torta chocolate", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 13

            item { // inicio item 14
                NavigationDrawerItem(
                    label = { Text("Galletas veganas", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Galletas veganas")
                        val precio = "4500"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.Cookie , contentDescription = "Galletas veganas", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 14

            item { // inicio item 15
                NavigationDrawerItem(
                    label = { Text("Torta de Boda", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Torta de Boda")
                        val precio = "60000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.Cake , contentDescription = "Torta de Boda", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 15

            item { // inicio item 16
                NavigationDrawerItem(
                    label = { Text("Torta de cumpleaños", fontSize = itemTextSize)},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Torta de cumpleaños")
                        val precio = "55000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.Cake , contentDescription = "Torta de cumpleaños", modifier = Modifier.size(itemIconSize)) },
                    colors = itemColors
                )
                Divider(color = separador, thickness = 1.dp)
            } // fin item 16



        } // fin LazyColumn

        // Footer
        Text(
            text = "@ 2025 Pasteleria Mil Sabores",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFCEB487))
                .padding(16.dp),
            textAlign = TextAlign.Center

        )

    } // fin columna
} // fin DrawerMenu


@Preview(showBackground = true)
@Composable
fun DrawerMenuPreview(){
    val navController = androidx.navigation.compose.rememberNavController()
    DrawerMenu(username = "Usuario Prueba", navController = navController)
}