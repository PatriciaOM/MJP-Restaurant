package com.example.mjprestaurant.model.dish

/**
 * Resposta base del servidor per a operacions amb plats.
 *
 * @property messageStatus Estat del missatge
 * @property dishes Llista de plats
 *
 * @see Dish
 *
 * @author Martin Muñoz Pozuelo
 */
data class DishResponse(
    val messageStatus: String,
    val dishes: List<Dish>
)