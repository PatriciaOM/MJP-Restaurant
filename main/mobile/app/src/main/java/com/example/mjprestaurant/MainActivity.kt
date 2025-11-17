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
 * - Gestionar els rols d'usuari (admin vs user)
 *
 * @see LoginScreen
 * @see TablesScreen
 * @see TableDetailScreen
 * @see DishListScreen
 * @see DishFormScreen
 * @see PlaceholderScreen
 * @see LoginViewModel
 * @see TablesViewModel
 * @see DishViewModel
 * @see TableSessionViewModel
 *
 * @author Martin Muñoz Pozuelo
 * @since 1.0
 */
class MainActivity : ComponentActivity() {

    /**
     * Mètode trucat quan l'activitat és creada.
     *
     * Configura el contingut composable de l'activitat i inicialitza
     * el graf de navegació amb totes les pantalles de l'aplicació.
     *
     * @param savedInstanceState Estat previ de l'activitat si existeix
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura el contingut composable de l'activitat
        setContent {
            MJPRestaurantTheme {
                // Controlador de navegació que gestiona la pila de pantalles
                val navController = rememberNavController()

                // ViewModels compartits entre pantalles que mantenen l'estat de l'aplicació
                val loginViewModel: LoginViewModel = viewModel()
                val tablesViewModel: TablesViewModel = viewModel()
                val dishViewModel: DishViewModel = viewModel()

                // NOU: ViewModel per a la gestió de sessions de taula
                val tableSessionViewModel: TableSessionViewModel = viewModel()

                // Configuració del graf de navegació principal
                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.route
                ) {
                    /**
                     * Pantalla d'inici de sessió
                     *
                     * És la pantalla inicial de l'aplicació. Després d'un login exitós,
                     * navega a la pantalla de taules i neteja la pila de navegació.
                     */
                    composable(Screen.Login.route) {
                        LoginScreen(
                            viewModel = loginViewModel,
                            onLoginSuccess = {
                                // Navega a pantalla de taules i neteja el backstack
                                navController.navigate(Screen.Tables.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            }
                        )
                    }

                    /**
                     * Pantalla de llistat de taules
                     *
                     * Mostra l'estat de totes les taules del restaurant.
                     * Permet als administradors accedir a la gestió de plats.
                     * Permet als cambrers entrar al detall d'una taula.
                     */
                    composable(Screen.Tables.route) {
                        TablesScreen(
                            tablesViewModel = tablesViewModel,
                            loginViewModel = loginViewModel,
                            onLogout = {
                                // Tanca sessió i torna a la pantalla de login
                                loginViewModel.onLogoutClick()
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(Screen.Tables.route) { inclusive = true }
                                }
                            },
                            onGestioPlats = {
                                // Navega a la gestió de plats (només per administradors)
                                navController.navigate(Screen.DishList.route)
                            },
                            onTableClick = { tableId ->
                                // NOU: Navega al detall de la taula seleccionada
                                navController.navigate(Screen.TableDetail.createRoute(tableId))
                            }
                        )
                    }

                    /**
                     * Pantalla de detall de taula (NOU)
                     * * Permet obrir taula (crear sessió) o veure la comanda activa.
                     */
                    composable(
                        route = Screen.TableDetail.routeWithArgs,
                        arguments = Screen.TableDetail.arguments
                    ) { backStackEntry ->
                        // Recuperar l'ID de la taula dels arguments
                        val tableId = backStackEntry.arguments?.getLong(Screen.TableDetail.tableIdArg) ?: 0L

                        TableDetailScreen(
                            tableId = tableId,
                            tableSessionViewModel = tableSessionViewModel,
                            loginViewModel = loginViewModel,
                            onBack = {
                                // IMPORTANT: Refresquem les taules en tornar per actualitzar els colors (lliure/ocupada)
                                loginViewModel.token.value?.let { tablesViewModel.refreshTables(it) }
                                navController.popBackStack()
                            },
                            onAddDish = {
                                // TODO: Implementar navegació per afegir plats (pròxim pas)
                            }
                        )
                    }

                    /**
                     * Pantalla de llistat de plats
                     *
                     * Mostra tots els plats del menú amb opcions de filtre i cerca.
                     * Permet als administradors crear, editar i eliminar plats.
                     */
                    composable(Screen.DishList.route) {
                        DishListScreen(
                            dishViewModel = dishViewModel,
                            loginViewModel = loginViewModel,
                            esAdmin = loginViewModel.role.value == "admin",
                            onNewDish = {
                                // Navega al formulari per crear un nou plat
                                navController.navigate(Screen.DishForm.routeCreate)
                            },
                            onEditDish = { dishId ->
                                // Navega al formulari per editar un plat existent
                                // Utilitza la ruta amb l'ID del plat com a argument
                                navController.navigate(Screen.DishForm.editRoute(dishId))
                            },
                            onGoBack = {
                                // Torna a la pantalla anterior (taules)
                                navController.popBackStack()
                            }
                        )
                    }

                    /**
                     * Ruta per CREAR nous plats
                     *
                     * Pantalla de formulari sense arguments que s'utilitza
                     * quan es vol afegir un plat nou al menú.
                     *
                     * Ruta: "dish_form"
                     */
                    composable(Screen.DishForm.routeCreate) {
                        DishFormScreen(
                            dishId = null, // null indica que es vol crear un plat nou
                            dishViewModel = dishViewModel,
                            loginViewModel = loginViewModel,
                            onSaveSuccess = {
                                // Després de desar exitosament, torna al llistat de plats
                                navController.popBackStack(Screen.DishList.route, inclusive = false)
                            },
                            onCancel = {
                                // Cancel·la l'operació i torna enrere
                                navController.popBackStack()
                            }
                        )
                    }

                    /**
                     * Ruta per EDITAR plats existents
                     *
                     * Pantalla de formulari amb argument dishId que s'utilitza
                     * quan es vol modificar un plat existent del menú.
                     *
                     * Ruta: "dish_form/{dishId}"
                     */
                    composable(
                        route = Screen.DishForm.routeEdit,
                        arguments = Screen.DishForm.arguments
                    ) { navBackStackEntry ->
                        // Extrau l'ID del plat dels arguments de navegació
                        val dishId = Screen.DishForm.getDishId(navBackStackEntry)

                        DishFormScreen(
                            dishId = dishId, // ID del plat a editar
                            dishViewModel = dishViewModel,
                            loginViewModel = loginViewModel,
                            onSaveSuccess = {
                                // Després de desar exitosament, torna al llistat de plats
                                navController.popBackStack(Screen.DishList.route, inclusive = false)
                            },
                            onCancel = {
                                // Cancel·la l'operació i torna enrere
                                navController.popBackStack()
                            }
                        )
                    }

                    /**
                     * Pantalla principal temporal (placeholder)
                     *
                     * Pantalla de desenvolupament que es mostra després del login.
                     * Serà reemplaçada per la funcionalitat completa de comandes.
                     */
                    composable(Screen.Placeholder.route) {
                        PlaceholderScreen(
                            viewModel = loginViewModel,
                            onLogout = {
                                // Tanca sessió i torna a la pantalla de login
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