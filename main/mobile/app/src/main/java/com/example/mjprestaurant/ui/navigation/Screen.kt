package com.example.mjprestaurant.ui.navigation

/**
 * Sealed class que defineix les pantalles disponibles a l'aplicació
 *
 * Imlementa el patró de navegació amb Compose Navigation:
 *  - Cada pantalla té una ruta única
 *  - Les rutes s'utilitzen per a navegar entre pantalles.
 *  - Facilita la gestió centralitzada de la navegació.
 *
 *  @property route Ruta única que identifica la pantalla en el graf de navegació.
 *
 *  @see MainActivity
 *  @see NavHost
 *
 *  @author Martin Muñoz Pozuelo
 */
sealed class Screen(val route: String) {
    /**
     * Pantalla d'inici de sessió.
     *
     * Es la pantalla inicial de l'aplicació i permet:
     *  - Autenticació d'usuaris.
     *  - Validació de credencials.
     *  - Navegació a pantalla principal tras login exitos.
     *
     *  @see LoginScreen
     */
    object Login : Screen("login")

    /**
     * Pantalla de  llistat de taules.
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
     * Pantalla principal temporal (placeholder).
     *
     * Pantalla de prova que:
     * - Mostra informació de l'usuari autenticat.
     * - Permet tancar sessió
     * - Actua com a pantalla principal tras login exitos.
     *
     * @see PlaceholderScreen
     */
    object Placeholder : Screen("placeholder")
}