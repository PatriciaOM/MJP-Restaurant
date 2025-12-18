package com.example.mjprestaurant.model.order.request

import com.example.mjprestaurant.model.order.OrderItem
import com.example.mjprestaurant.model.order.Order

/**
 * Petició per crear una línia de comanda.
 * Coincideix amb OrderItemCreateInfo del servidor.
 */
data class OrderItemCreateInfo(
    val sessionToken: String,
    val newEntry: OrderItem // En el JSON del servidor es diu "newEntry" (heretat de CreateInfo)
)


/**
 * Petició per obtenir línies de comanda.
 *
 * @property searchType Tipus de cerca (ALL, BY_ID, BY_ORDER).
 * @property id L'ID que busquem (pot ser l'ID de l'item o l'ID de l'ordre segons el tipus).
 */
data class OrderItemGetInfo(
    val sessionToken: String,
    val searchType: SearchType,
    val id: Long? = null,
    val orderId: Long? = null
) {
    enum class SearchType {
        ALL,
        BY_ID,
        BY_ORDER_ID
    }
}
