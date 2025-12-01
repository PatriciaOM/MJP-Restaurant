package com.example.mjprestaurant.model.order.request

import com.example.mjprestaurant.model.order.Order

/**
 * Petició per crear una nova comanda.
 * Replica l'estructura de OrderCreateInfo del servidor.
 */
data class OrderCreateInfo(
    val sessionToken: String,
    val newEntry: Order
)

/**
 * Petició per obtenir comandes.
 * Replica l'estructura de OrderGetInfo del servidor.
 *
 * IMPORTANT: Utilitzarem BY_SESSION_SERVICE per obtenir la comanda d'una taula.
 */
data class OrderGetInfo(
    val sessionToken: String,
    val searchType: SearchType,
    val id: Long? = null,
    val sessionServiceId: Long? = null
) {
    enum class SearchType {
        ALL,
        BY_ID,
        BY_SESSION_SERVICE_ID // Cerca per ID de sessió
    }
}

/**
 * Petició per actualitzar una comanda (capçalera).
 * Replica l'estructura de OrderUpdateInfo del servidor.
 * * @property sessionToken Token de sessió.
 * @property item L'objecte Order amb les dades actualitzades.
 */
data class OrderUpdateInfo(
    val sessionToken: String,
    val item: Order
)

/**
 * Petició per eliminar una comanda.
 * Replica l'estructura de OrderDeleteInfo del servidor.
 *
 * @property sessionToken Token de sessió.
 * @property id ID de la comanda a eliminar.
 */
data class OrderDeleteInfo(
    val sessionToken: String,
    val id: Long
)