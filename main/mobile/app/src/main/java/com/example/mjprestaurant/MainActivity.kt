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
import com.example.mjprestaurant.ui.screens.OrderScreen
import com.example.mjprestaurant.ui.screens.PlaceholderScreen
import com.example.mjprestaurant.ui.screens.TableDetailScreen
import com.example.mjprestaurant.ui.screens.TablesScreen
import com.example.mjprestaurant.ui.theme.MJPRestaurantTheme
import com.example.mjprestaurant.viewmodel.DishViewModel
import com.example.mjprestaurant.viewmodel.LoginViewModel
import com.example.mjprestaurant.viewmodel.TableSessionViewModel
import com.example.mjprestaurant.viewmodel.TablesViewModel

/**
 * Activitat principal de l'aplicació MJPRestaurant.
 *
 * Aquesta activitat s'encarrega de:
 * - Configurar el tema de l'aplicació
 * - Inicialitzar el sistema de navegació
 * - Gestionar el cicle de vida dels ViewModels
 * - Coordinar la navegació entre pantalles
 *
 * @author Martin Muñoz Pozuelo
 * @since 1.0
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MJPRestaurantTheme {
                val navController = rememberNavController()

                // ViewModels compartits
                val loginViewModel: LoginViewModel = viewModel()
                val tablesViewModel: TablesViewModel = viewModel()
                val dishViewModel: DishViewModel = viewModel()
                val tableSessionViewModel: TableSessionViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.route
                ) {
                    // --- LOGIN ---
                    composable(Screen.Login.route) {
                        LoginScreen(
                            viewModel = loginViewModel,
                            onLoginSuccess = {
                                navController.navigate(Screen.Tables.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            }
                        )
                    }

                    // --- TAULES ---
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
                                navController.navigate(Screen.DishList.route)
                            },
                            onTableClick = { tableId ->
                                // LÒGICA DE NAVEGACIÓ INTEL·LIGENT
                                // Busquem l'estat de la taula clicada
                                val taula = tablesViewModel.taules.value.find { it.id == tableId }

                                if (taula != null && taula.estaOcupada()) {
                                    // Si està ocupada -> Anem directe a la Comanda (TPV)
                                    navController.navigate(Screen.Order.createRoute(tableId))
                                } else {
                                    // Si està lliure -> Anem a obrir la taula
                                    navController.navigate(Screen.TableDetail.createRoute(tableId))
                                }
                            }
                        )
                    }

                    // --- DETALL TAULA (OBRIR SESSIÓ) ---
                    composable(
                        route = Screen.TableDetail.routeWithArgs,
                        arguments = Screen.TableDetail.arguments
                    ) { backStackEntry ->
                        val tableId = backStackEntry.arguments?.getLong(Screen.TableDetail.tableIdArg) ?: 0L

                        TableDetailScreen(
                            tableId = tableId,
                            tableSessionViewModel = tableSessionViewModel,
                            tablesViewModel = tablesViewModel, // <--- AFEGIT AQUÍ
                            loginViewModel = loginViewModel,
                            onBack = {
                                // Refresquem taules per si hem cancel·lat alguna operació
                                loginViewModel.token.value?.let { tablesViewModel.refreshTables(it) }
                                navController.popBackStack()
                            },
                            onTableOpened = {
                                // Quan la taula s'ha obert amb èxit, saltem a la comanda
                                // i treiem la pantalla de "Obrir" de la pila perquè no s'hi pugui tornar
                                navController.navigate(Screen.Order.createRoute(tableId)) {
                                    popUpTo(Screen.TableDetail.routeWithArgs) { inclusive = true }
                                }
                            }
                        )
                    }

                    // --- COMANDA (TPV VISUAL) ---
                    composable(
                        route = Screen.Order.routeWithArgs,
                        arguments = Screen.Order.arguments
                    ) { backStackEntry ->
                        val tableId = backStackEntry.arguments?.getLong(Screen.Order.tableIdArg) ?: 0L

                        OrderScreen(
                            tableId = tableId,
                            tableSessionViewModel = tableSessionViewModel,
                            dishViewModel = dishViewModel,
                            loginViewModel = loginViewModel,
                            onBack = {
                                // En tornar a la llista, refresquem per veure que ara està ocupada
                                loginViewModel.token.value?.let { tablesViewModel.refreshTables(it) }
                                navController.popBackStack(Screen.Tables.route, inclusive = false)
                            }
                        )
                    }

                    // --- LLISTAT PLATS (ADMIN) ---
                    composable(Screen.DishList.route) {
                        DishListScreen(
                            dishViewModel = dishViewModel,
                            loginViewModel = loginViewModel,
                            esAdmin = loginViewModel.role.value == "admin",
                            onNewDish = {
                                navController.navigate(Screen.DishForm.routeCreate)
                            },
                            onEditDish = { dishId ->
                                navController.navigate(Screen.DishForm.editRoute(dishId))
                            },
                            onGoBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // --- FORMULARI PLATS (CREAR) ---
                    composable(Screen.DishForm.routeCreate) {
                        DishFormScreen(
                            dishId = null,
                            dishViewModel = dishViewModel,
                            loginViewModel = loginViewModel,
                            onSaveSuccess = {
                                navController.popBackStack(Screen.DishList.route, inclusive = false)
                            },
                            onCancel = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // --- FORMULARI PLATS (EDITAR) ---
                    composable(
                        route = Screen.DishForm.routeEdit,
                        arguments = Screen.DishForm.arguments
                    ) { navBackStackEntry ->
                        val dishId = Screen.DishForm.getDishId(navBackStackEntry)

                        DishFormScreen(
                            dishId = dishId,
                            dishViewModel = dishViewModel,
                            loginViewModel = loginViewModel,
                            onSaveSuccess = {
                                navController.popBackStack(Screen.DishList.route, inclusive = false)
                            },
                            onCancel = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // --- PLACEHOLDER ---
                    composable(Screen.Placeholder.route) {
                        PlaceholderScreen(
                            viewModel = loginViewModel,
                            onLogout = {
                                loginViewModel.onLogoutClick()
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