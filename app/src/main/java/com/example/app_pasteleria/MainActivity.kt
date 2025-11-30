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
import com.example.app_pasteleria.navigation.AppNav
import com.example.app_pasteleria.ui.theme.ApiRestTheme
import com.example.app_pasteleria.viewmodel.PostreViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppNav()
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