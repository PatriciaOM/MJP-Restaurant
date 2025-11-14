package com.example.mjprestaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mjprestaurant.ui.navigation.Screen
import com.example.mjprestaurant.ui.screens.DishFormScreen
import com.example.mjprestaurant.ui.screens.DishListScreen
import com.example.mjprestaurant.ui.screens.LoginScreen
import com.example.mjprestaurant.ui.screens.PlaceholderScreen
import com.example.mjprestaurant.ui.screens.TablesScreen
import com.example.mjprestaurant.ui.theme.MJPRestaurantTheme
import com.example.mjprestaurant.viewmodel.DishViewModel
import com.example.mjprestaurant.viewmodel.LoginViewModel
import com.example.mjprestaurant.viewmodel.TablesViewModel

/**
 * Activitat principal de l'aplicació MJPRestaurant.
 *
 * Aquesta activitat s'encarrega de:
 * - Configurar el tema de l'aplicació
 * - Inicialitzar el sistema de navegació
 * - Gestionar el cicle de vida dels ViewModels
 * - Coordinar la navegació entre pantalles
 * - Gestionar els rols d'usuari (admin vs user)
 *
 * @see LoginScreen
 * @see TablesScreen
 * @see DishListScreen
 * @see DishFormScreen
 * @see PlaceholderScreen
 * @see LoginViewModel
 * @see TablesViewModel
 * @see DishViewModel
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
                // ViewModels compartits entre pantalles
                val loginViewModel: LoginViewModel = viewModel()
                val tablesViewModel: TablesViewModel = viewModel()
                val dishViewModel: DishViewModel = viewModel()

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
                            tablesViewModel = tablesViewModel,
                            loginViewModel = loginViewModel,
                            onLogout = {
                                loginViewModel.onLogoutClick()
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(Screen.Tables.route) { inclusive = true }
                                }
                            },
                            onGestioPlats = {
                                // Navega a gestió de plats
                                navController.navigate(Screen.DishList.route)
                            }
                        )
                    }

                    // Definició de la pantalla de llistat de plats
                    composable(Screen.DishList.route) {
                        DishListScreen(
                            dishViewModel = dishViewModel,
                            loginViewModel = loginViewModel,
                            esAdmin = loginViewModel.role.value == "admin",
                            onNewDish = {
                                // Navega a formulari per crear nou plat
                                navController.navigate(Screen.DishForm.route)
                            },
                            onEditDish = { dishId ->
                                // Navega a formulari per editar plat existent
                                navController.navigate("${Screen.DishForm.route}/$dishId")
                            },
                            onGoBack = {
                                // Torna a la pantalla anterior
                                navController.popBackStack()
                            }
                        )
                    }

                    // Definició de la pantalla de formulari de plat (crear - sense arguments)
                    composable(Screen.DishForm.route) {
                        DishFormScreen(
                            dishId = null,
                            dishViewModel = dishViewModel,
                            loginViewModel = loginViewModel,
                            onSaveSuccess = {
                                // Torna al llistat de plats després de desar
                                navController.popBackStack(Screen.DishList.route, inclusive = false)
                            },
                            onCancel = {
                                // Torna enrere sense desar
                                navController.popBackStack()
                            }
                        )
                    }

                    // Definició de la pantalla de formulari de plat (editar - amb arguments)
                    composable(
                        route = Screen.DishForm.routeWithArgs,
                        arguments = Screen.DishForm.arguments
                    ) { navBackStackEntry ->
                        // Obtenir el dishId dels arguments de navegació
                        val dishId = navBackStackEntry.arguments?.getInt(Screen.DishForm.dishIdArg)
                        val actualDishId = if (dishId == -1) null else dishId

                        DishFormScreen(
                            dishId = actualDishId,
                            dishViewModel = dishViewModel,
                            loginViewModel = loginViewModel,
                            onSaveSuccess = {
                                // Torna al llistat de plats després de desar
                                navController.popBackStack(Screen.DishList.route, inclusive = false)
                            },
                            onCancel = {
                                // Torna enrere sense desar
                                navController.popBackStack()
                            }
                        )
                    }

                    // Definició de la pantalla principal temporal
                    composable(Screen.Placeholder.route) {
                        PlaceholderScreen(
                            viewModel = loginViewModel,
                            onLogout = {
                                loginViewModel.onLogoutClick()
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