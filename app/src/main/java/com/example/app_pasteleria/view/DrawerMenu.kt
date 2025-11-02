package com.example.app_pasteleria.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable

fun DrawerMenu(
    username: String,
    navController: NavController
){ //inicio

    Column(modifier = Modifier.fillMaxSize())
    { // inicio columna
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp) // dp: densidad de pixeles
                .background(MaterialTheme.colorScheme.primary)
        ) // fin box
        { // inicio contenido
            Text(
                text = "Catálogo de productos",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        } // fin contenido

        // LazyColumn: crear lista de elementos que se pueden desplazar verticalmente
        LazyColumn(modifier = Modifier.weight(1f)){

            item { // inicio item 1
                NavigationDrawerItem(
                    label = { Text("Torta Cuadrada de Frutas")},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Torta Cuadrada de Frutas")
                        val precio = "50000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.Cake , contentDescription = "Torta Frutas") }
                )
            } // fin item 1

            item { // inicio item 1
                NavigationDrawerItem(
                    label = { Text("Torta Cuadrada de Chocolate")},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Torta Cuadrada de Chocolate")
                        val precio = "45000"
                        navController.navigate("CatalogoFormScreen/$nombre/$precio")
                    }, // fin OnClick
                    icon = { Icon(Icons.Default.Cake , contentDescription = "Torta Chocolate") }
                )
            } // fin item 1



        } // fin LazyColumn

        // Footer
        Text(
            text = "@ 2025 Burger App",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
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