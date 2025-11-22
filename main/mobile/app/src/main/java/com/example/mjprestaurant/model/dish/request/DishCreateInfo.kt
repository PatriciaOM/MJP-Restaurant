package com.example.mjprestaurant.model.dish.request

import com.example.mjprestaurant.model.dish.Dish

/**
 * Petició per crear un nou plat.
 * Adaptada al servidor.
 *
 * @property sessionToken Token de sessió de l'usuari
 * @property dish Plat a crear
 *
 * @see Dish
 * @see DishCreateResponse
 *
 * @author Martin Muñoz Pozuelo
 */
data class DishCreateInfo(
    val sessionToken: String,
    val newEntry: Dish
)