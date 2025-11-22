package com.example.mjprestaurant.model.order

import com.google.gson.annotations.SerializedName

/**
 * Representa una comanda (OrderRestaurant al servidor).
 *
 * @property id ID únic de la comanda
 * @property idSessionService ID de la sessió a la qual pertany
 * @property date Data de la comanda
 * @property state Estat de la comanda (OPEN, SENDED, SERVED)
 *
 * @author Martin Muñoz Pozuelo
 */
data class Order(
    val id: Long? = null,
    val idSessionService: Long,
    val date: String? = null, // LocalDate com String
    @SerializedName("state")
    val state: OrderStatus
)

/**
 * Estats possibles d'una comanda.
 */
enum class OrderStatus {
    OPEN,
    SENDED,
    SERVED
}