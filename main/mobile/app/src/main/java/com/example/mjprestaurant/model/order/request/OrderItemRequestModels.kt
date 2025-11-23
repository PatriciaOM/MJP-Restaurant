package com.example.mjprestaurant.model.order.request

import com.example.mjprestaurant.model.order.OrderItem

/**
 * Petició per crear una línia de comanda.
 * Coincideix amb OrderItemCreateInfo del servidor.
 */
data class OrderItemCreateInfo(
    val sessionToken: String,
    val newEntry: OrderItem // En el JSON del servidor es diu "newEntry" (heretat de CreateInfo)
)