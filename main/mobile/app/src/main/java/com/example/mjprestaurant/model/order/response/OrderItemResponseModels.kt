package com.example.mjprestaurant.model.order.response

import com.example.mjprestaurant.model.order.OrderItem
import com.google.gson.annotations.SerializedName

/**
 * Resposta del servidor per a operacions amb OrderItem.
 */
data class OrderItemResponse(
    val messageStatus: String,
    @SerializedName("items") // En Java es diu "items" (minúscula)
    val items: List<OrderItem>
)