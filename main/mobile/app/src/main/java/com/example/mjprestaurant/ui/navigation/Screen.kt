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
     * @see DishFormScreen
     */
    object DishForm : Screen("dish_form") {

        /**
         * Argument per identificar el plat a editar
         * -1 indica que es vol crear un nou plat
         */
        const val dishIdArg = "dishId"

        /**
         * Ruta base amb argument per a la navegació
         */
        val routeWithArgs = "${route}/{$dishIdArg}"

        /**
         * Llista d'arguments per a la navegació
         * Defineix el tipus i valors per defecte dels paràmetres
         */
        val arguments = listOf(
            navArgument(dishIdArg) {
                type = NavType.IntType
                nullable = false
                defaultValue = -1  // Utilitzem -1 per indicar "nou plat"
            }
        )

        /**
         * Crea la ruta amb l'ID del plat
         *
         * @param dishId ID del plat a editar (null o -1 per crear nou)
         * @return Ruta completa amb l'argument
         */
        fun createRoute(dishId: Int?): String {
            return if (dishId != null && dishId != -1) {
                "${route}/$dishId"
            } else {
                route
            }
        }

        /**
         * Extrau l'ID del plat des de la ruta de navegació
         *
         * @param dishIdString ID del plat com a string
         * @return ID del plat o null si és un plat nou
         */
        fun getDishId(dishIdString: String?): Int? {
            val id = dishIdString?.toIntOrNull()
            return if (id == -1) null else id
        }
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