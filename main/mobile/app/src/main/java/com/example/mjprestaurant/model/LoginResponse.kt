package com.example.mjprestaurant.model

/**
 * Resposta del servidor per a peticions de login exitoses.
 *
 * @property token Token de sessió per autenticar requests futures.
 * @property role Rol de l'usuari autenticat ("user", "admin")
 *
 * @see LoginRequest
 * @see AuthRepository
 *
 * @author Martin Muñoz Pozuelo
 */
    data class LoginResponse(
        val token: String,
        val role: String
    )
