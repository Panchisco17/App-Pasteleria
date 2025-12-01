package com.example.app_pasteleria.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_pasteleria.ui.login.LoginScreen
import com.example.app_pasteleria.ui.register.RegisterScreen
import com.example.app_pasteleria.view.DrawerMenu
import com.example.app_pasteleria.view.CatalogoFormScreen
import com.example.app_pasteleria.view.CarritoScreen // <--- Importante: Importar la nueva pantalla

@Composable
fun AppNav(){
    //Crear el controlador de navegación
    val navController = rememberNavController()

    NavHost(navController= navController, startDestination = "login")
    {
        // 1. Pantalla de Login
        composable("login"){
            LoginScreen(navController = navController)
        }

        // 2. Pantalla de Registro
        composable("register") {
            RegisterScreen(navController = navController)
        }

        // 3. Menú Lateral (Catálogo)
        composable(
            route="DrawerMenu/{username}",
            arguments = listOf(
                navArgument("username"){ type = NavType.StringType }
            )
        ){ backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            DrawerMenu(username= username, navController= navController)
        }

        // 4. Formulario de Compra (Detalle del Pastel)
        composable(
            route="CatalogoFormScreen/{nombre}/{precio}",
            arguments = listOf(
                navArgument("nombre"){ type = NavType.StringType },
                navArgument("precio"){ type = NavType.StringType }
            )
        ){ backStackEntry ->
            val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre") ?:"")
            val precio = backStackEntry.arguments?.getString("precio") ?:""

            CatalogoFormScreen(navController = navController, nombre= nombre, precio= precio)
        }

        // 5. Pantalla del Carrito de Compras (NUEVA RUTA)
        composable("CarritoScreen") {
            CarritoScreen(navController = navController)
        }

    }//fin NavHost
}//fin appNav