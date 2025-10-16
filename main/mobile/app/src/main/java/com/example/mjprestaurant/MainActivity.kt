package com.example.mjprestaurant
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mjprestaurant.ui.navigation.Screen
import com.example.mjprestaurant.ui.screens.LoginScreen
import com.example.mjprestaurant.ui.screens.PlaceholderScreen
import com.example.mjprestaurant.ui.theme.MJPRestaurantTheme
import com.example.mjprestaurant.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MJPRestaurantTheme {
                val navController = rememberNavController()
                val loginViewModel: LoginViewModel = viewModel()

                NavHost(navController = navController, startDestination = Screen.Login.route) {
                    composable(Screen.Login.route) {
                        LoginScreen(viewModel = loginViewModel, onLoginSuccess = {
                            navController.navigate(Screen.Placeholder.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        })
                    }

                    composable(Screen.Placeholder.route) {
                        PlaceholderScreen(viewModel = loginViewModel, onLogout = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Placeholder.route) { inclusive = true }
                            }
                        })
                    }
                }
            }
        }
    }
}