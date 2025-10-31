package com.example.mjprestaurant.model

/**
 * Petició per obtenir l'estat de les taules.
 *
 * @property sessionToken Token de sessió de l'usuari autenticat
 * @property taulaId ID opcional d'una taula específica
 */
data class TableStatusRequest(
    val sessionToken: String,
    val taulaId: Int? = null
)