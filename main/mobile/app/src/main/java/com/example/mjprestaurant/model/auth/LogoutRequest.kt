package com.example.mjprestaurant.model.auth

/**
 * Petició per tancar sessió d'usuari.
 *
 * @property sessionToken Token de sessió actual a invalidar.
 *
 * @see LoginViewModel
 * @see AuthRepository
 *
 * @author Martin Muñoz Pozuelo
 */
data class LogoutRequest(
    val sessionToken: String
)