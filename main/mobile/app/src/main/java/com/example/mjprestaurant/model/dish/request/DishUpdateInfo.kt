package com.example.mjprestaurant.model.dish.request

import com.example.mjprestaurant.model.dish.Dish

/**
 * Petició per actualitzar un plat.
 * Adaptada al servidor.
 *
 * @property sessionToken Token de sessió de l'usuari
 * @property dish Plat actualitzat
 *
 * @see Dish
 * @see DishUpdateResponse
 *
 * @author Martin Muñoz Pozuelo
 */
data class DishUpdateInfo(
    val sessionToken: String,
    val newEntry: Dish
)