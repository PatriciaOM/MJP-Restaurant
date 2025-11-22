package com.example.mjprestaurant.model.order

import com.example.mjprestaurant.model.dish.DishCategory

/**
 * Representa una línia de comanda.
 * Adaptat al model del servidor (mjp.server.ServerMJP.database.OrderItem).
 *
 * @property id ID únic de la línia.
 * @property idOrder ID de la comanda a la qual pertany.
 * @property idDish ID del plat demanat.
 * @property amount Quantitat de plats.
 * @property price Preu unitari.
 * @property name Nom del plat.
 * @property description Descripció del plat.
 * @property category Categoria del plat.
 */
data class OrderItem(
    val id: Long? = null,
    val idOrder: Long,
    val idDish: Long,
    val amount: Int,
    val price: Float,
    val name: String,
    val description: String,
    val category: DishCategory
)