package com.example.mjprestaurant.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Sealed class que defineix les pantalles disponibles a l'aplicació
 *
 * Implementa el patró de navegació amb Compose Navigation:
 *  - Cada pantalla té una ruta única
 *  - Les rutes s'utilitzen per a navegar entre pantalles
 *  - Facilita la gestió centralitzada de la navegació
 *
 *  @property route Ruta única que identifica la pantalla en el graf de navegació
 *
 *  @see MainActivity
 *  @see NavHost
 *
 *  @author Martin Muñoz Pozuelo
 */
sealed class Screen(val route: String) {
    /**
     * Pantalla d'inici de sessió
     *
     * És la pantalla inicial de l'aplicació i permet:
     *  - Autenticació d'usuaris
     *  - Validació de credencials
     *  - Navegació a pantalla principal tras login exitós
     *
     *  @see LoginScreen
     */
    object Login : Screen("login")

    /**
     * Pantalla de llistat de taules
     *
     * Mostra totes les taules del restaurant amb el seu estat actual:
     * - Taules lliures i ocupades
     * - Capacitat i comensals actuals
     * - Permet seleccionar taules per a gestionar-les
     *
     * @see TablesScreen
     */
    object Tables : Screen("tables")

    /**
     * Pantalla de llistat de plats del restaurant
     *
     * Mostra tots els plats del menú amb opcions de:
     * - Filtrar per categoria
     * - Cercar per nom o descripció
     * - Crear nous plats (només admin)
     * - Editar o eliminar plats existents (només admin)
     *
     * @see DishListScreen
     */
    object DishList : Screen("dish_list")

    /**
     * Pantalla de formulari per crear o editar plats
     *
     * Permet als administradors:
     * - Crear nous plats al menú
     * - Editar plats existents
     * - Validar les dades abans de desar
     *
     * Utilitza dues rutes separades per a una navegació més clara:
     * - Ruta sense arguments per a crear nous plats
     * - Ruta amb argument dishId per a editar plats existents
     *
     * @see DishFormScreen
     */
    object DishForm : Screen("dish_form") {

        /**
         * Nom de l'argument per identificar el plat a editar
         * S'utilitza a la ruta d'edició per a passar l'ID del plat
         */
        const val dishIdArg = "dishId"

        /**
         * Ruta per crear nous plats
         * No requereix arguments, s'utilitza quan es vol afegir un plat nou
         */
        val routeCreate = route

        /**
         * Ruta per editar plats existents
         * Inclou l'argument dishId com a paràmetre de ruta
         */
        val routeEdit = "${route}/{$dishIdArg}"

        /**
         * Llista d'arguments per a la navegació d'edició
         * Defineix el tipus i valors per defecte del paràmetre dishId
         */
        val arguments = listOf(
            navArgument(dishIdArg) {
                type = NavType.LongType
                nullable = false
                defaultValue = 0L
            }
        )

        /**
         * Crea la ruta per a crear un nou plat
         *
         * @return Ruta sense arguments per a la creació de plats
         */
        fun createRoute(): String = routeCreate

        /**
         * Crea la ruta per a editar un plat existent
         *
         * @param dishId ID del plat a editar
         * @return Ruta amb l'argument dishId per a l'edició
         */
        fun editRoute(dishId: Long): String = "${route}/$dishId"

        /**
         * Extrau l'ID del plat des de la ruta de navegació
         *
         * @param navBackStackEntry Entrada de la pila de navegació
         * @return ID del plat o null si no s'ha proporcionat (cas de creació)
         */
        fun getDishId(navBackStackEntry: androidx.navigation.NavBackStackEntry): Long? {
            return navBackStackEntry.arguments?.getLong(dishIdArg)?.takeIf { it != 0L }
        }
    }

    /**
     * Pantalla de detall d'una taula específica.
     *
     * Permet:
     * - Obrir taula (crear sessió) si està lliure.
     * - Veure la comanda actual si està ocupada.
     * - Afegir plats a la comanda.
     */
    object TableDetail : Screen("table_detail") {
        const val tableIdArg = "tableId"

        val routeWithArgs = "$route/{$tableIdArg}"

        val arguments = listOf(
            navArgument(tableIdArg) {
                type = NavType.LongType
                nullable = false
            }
        )

        fun createRoute(tableId: Long): String = "$route/$tableId"
    }

    /**
     * Pantalla principal temporal (placeholder)
     *
     * Pantalla de prova que:
     * - Mostra informació de l'usuari autenticat
     * - Permet tancar sessió
     * - Actua com a pantalla principal tras login exitós
     *
     * @see PlaceholderScreen
     */
    object Placeholder : Screen("placeholder")
}