package com.example.app_pasteleria.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app_pasteleria.viewmodel.CatalogoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    viewModel: CatalogoViewModel = viewModel()
) {
    val productosEnCarrito by viewModel.pasteles.collectAsState()
    val enviando by viewModel.enviando.collectAsState()

    val total = productosEnCarrito.sumOf { it.precio.toDoubleOrNull() ?: 0.0 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF7C460D),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFEAD3AC)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (productosEnCarrito.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("El carrito está vacío 🛒", fontSize = 20.sp, color = Color(0xFF623608))
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(productosEnCarrito) { producto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Columna de Info (Nombre y Precio)
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = producto.nombre,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF623608),
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "$${producto.precio}",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.DarkGray
                                    )
                                }

                                // Controles CRUD (Menos, Cantidad, Mas, Borrar)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Botón Menos
                                    IconButton(
                                        onClick = { viewModel.actualizarCantidad(producto, false) },
                                        modifier = Modifier.size(30.dp).background(Color(0xFFE0E0E0), CircleShape)
                                    ) {
                                        Icon(Icons.Default.Remove, contentDescription = "Menos", modifier = Modifier.size(16.dp))
                                    }

                                    Text(
                                        text = producto.cantidad,
                                        modifier = Modifier.padding(horizontal = 12.dp),
                                        fontWeight = FontWeight.Bold
                                    )

                                    // Botón Mas
                                    IconButton(
                                        onClick = { viewModel.actualizarCantidad(producto, true) },
                                        modifier = Modifier.size(30.dp).background(Color(0xFFE0E0E0), CircleShape)
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Mas", modifier = Modifier.size(16.dp))
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    // Botón Eliminar (Basurero)
                                    IconButton(
                                        onClick = { viewModel.eliminarProducto(producto) }
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Eliminar",
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ... (El resto del resumen y botón finalizar sigue igual) ...

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFCEB487))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total a Pagar:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("$${total.toInt()}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.finalizarCompra {
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C460D)),
                    enabled = !enviando
                ) {
                    if (enviando) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Icon(Icons.Default.ShoppingCartCheckout, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Finalizar Compra")
                    }
                }
            }
        }
    }
}