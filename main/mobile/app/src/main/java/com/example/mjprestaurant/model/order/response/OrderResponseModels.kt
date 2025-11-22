package com.example.mjprestaurant.model.order.response

import com.example.mjprestaurant.model.order.Order
import com.google.gson.annotations.SerializedName

/**
 * Resposta genèrica per a operacions d'Order.
 *
 * IMPORTANT: El servidor Java defineix la llista com "Items" (amb majúscula).
 * Utilitzem @SerializedName per mapejar-ho correctament.
 */
data class OrderResponse(
    val messageStatus: String,
    @SerializedName("Items")
    val items: List<Order>
)