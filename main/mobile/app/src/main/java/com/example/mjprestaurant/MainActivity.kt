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
import com.example.mjprestaurant.ui.screens.TablesScreen
import com.example.mjprestaurant.ui.theme.MJPRestaurantTheme
import com.example.mjprestaurant.viewmodel.LoginViewModel

/**
 * Activitat principal de l'aplicació MJPRestaurant.
 *
 * Aquesta activitat s'encarrega de:
 * - Configurar el tema de l'aplicació
 * - Inicialitzar el sistema de navegació
 * - Gestionar el cicle de vida dels ViewModels
 * - Coordinar la navegació entre pantalles
 *
 * @see LoginScreen
 * @see TablesScreen
 * @see PlaceholderScreen
 * @see LoginViewModel
 *
 * @author Martin Muñoz Pozuelo
 * @since 1.0
 */
class MainActivity : ComponentActivity() {

    /**
     * Mètode trucat quan l'activitat és creada.
     *
     * @param savedInstanceState Estat previ de l'activitat si existeix
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura el contingut composable de l'activitat
        setContent {
            MJPRestaurantTheme {
                // Controlador de navegació
                val navController = rememberNavController()
                // ViewModel compartit entre pantalles
                val loginViewModel: LoginViewModel = viewModel()

                // Configuració del graf de navegació
                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.route
                ) {
                    // Definició de la pantalla de login
                    composable(Screen.Login.route) {
                        LoginScreen(
                            viewModel = loginViewModel,
                            onLoginSuccess = {
                                // Navega a pantalla de taules i neteja backstack
                                navController.navigate(Screen.Tables.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            }
                        )
                    }

                    // Definició de la pantalla de taules
                    composable(Screen.Tables.route) {
                        TablesScreen(
                            loginViewModel = loginViewModel,
                            onLogout = {
                                loginViewModel.onLogoutClick()
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(Screen.Tables.route) { inclusive = true }
                                }
                            }
                        )
                    }

                    // Definició de la pantalla principal temporal
                    composable(Screen.Placeholder.route) {
                        PlaceholderScreen(
                            viewModel = loginViewModel,
                            onLogout = {
                                // Navega a pantalla de login i neteja backstack
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(Screen.Placeholder.route) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}