package com.example.mjprestaurant.model.dish.request

/**
 * Petició per eliminar un plat.
 * Adaptada al servidor.
 *
 * @property sessionToken Token de sessió de l'usuari
 * @property id ID del plat a eliminar
 *
 * @see DishDeleteResponse
 *
 * @author Martin Muñoz Pozuelo
 */
data class DishDeleteInfo(
    val sessionToken: String,
    val id: Long
)