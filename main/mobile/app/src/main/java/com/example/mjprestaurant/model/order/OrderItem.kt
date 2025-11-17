package com.example.mjprestaurant.model.order

import com.example.mjprestaurant.model.dish.DishCategory

/**
 * Representa una línia de comanda.
 * Adaptat al model del servidor (mjp.server.ServerMJP.database.OrderItem).
 *
 * @property id ID únic de la línia.
 * @property idOrder ID de la comanda a la qual pertany.
 * @property amount Quantitat de plats (en Java es diu amount, no quantity).
 * @property price Preu unitari en el moment de la comanda.
 * @property description Nom o descripció del plat.
 * @property category Categoria del plat.
 */
data class OrderItem(
    val id: Long? = null,
    val idOrder: Long,
    val amount: Int,
    val price: Float,
    val description: String,
    val category: DishCategory
)