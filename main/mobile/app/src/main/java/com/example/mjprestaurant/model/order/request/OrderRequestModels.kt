package com.example.mjprestaurant.model.order.request

import com.example.mjprestaurant.model.order.Order

/**
 * Petició per crear una nova comanda.
 */
data class OrderCreateInfo(
    val sessionToken: String,
    val newEntry: Order
)

/**
 * Petició per obtenir comandes.
 * Inclou BY_SESSION_SERVICE per trobar la comanda d'una taula.
 */
data class OrderGetInfo(
    val sessionToken: String,
    val searchType: SearchType,
    val id: Long? = null
) {
    enum class SearchType {
        ALL,
        BY_ID,
        BY_SESSION_SERVICE
    }
}

/**
 * Petició per actualitzar una comanda.
 */
data class OrderUpdateInfo(
    val sessionToken: String,
    val item: Order
)

/**
 * Petició per eliminar una comanda.
 */
data class OrderDeleteInfo(
    val sessionToken: String,
    val id: Long
)