package com.example.mjprestaurant.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Placeholder : Screen("placeholder")
}