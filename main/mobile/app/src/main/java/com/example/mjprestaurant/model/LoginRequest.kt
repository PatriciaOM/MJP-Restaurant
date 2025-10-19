package com.example.mjprestaurant.model

/**
 * Petició d'autenticació d'usuari.
 *
 * @property username nom d'usuari
 * @property password Contrasenya de l'usuari
 * @property role Rol sol·licitat pel login
 *
 * @see LoginResponse
 * @see AuthRepository
 *
 * @author Martin Muñoz Pozuelo
 */
 data class LoginRequest(
        val username: String,
        val password: String,
        val role: String = "user"
    )
