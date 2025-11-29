package com.example.app_pasteleria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.app_pasteleria.ui.theme.AppPasteleriaTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_pasteleria.ui.screens.PostScreen
import com.example.app_pasteleria.ui.theme.ApiRestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permite que la app dibuje contenido debajo de las barras del sistema
        WindowCompat.setDecorFitsSystemWindows(window, false)
// Aquí inicia Jetpack Compose
        setContent {
            ApiRestTheme {
                val postViewModel: com.example.app_pasteleria.viewmodel.PostViewModel = viewModel()
                PostScreen(viewModel = postViewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppPasteleriaTheme {
        Greeting("Android")
    }
}