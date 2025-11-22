package com.example.mjprestaurant.model.session.request

import com.example.mjprestaurant.model.session.SessionService

/**
 * Petició per crear una nova sessió (Obrir taula).
 *
 * @property sessionToken Token de l'usuari
 * @property newEntry L'objecte SessionService a crear
 */
data class SessionServiceCreateInfo(
    val sessionToken: String,
    val newEntry: SessionService
)

/**
 * Petició per obtenir sessions.
 *
 * @property sessionToken Token de l'usuari
 * @property searchType Tipus de cerca (ALL, BY_ID)
 * @property id ID opcional per a la cerca
 */
data class SessionServiceGetInfo(
    val sessionToken: String,
    val searchType: SearchType = SearchType.ALL,
    val id: Long? = null
) {
    enum class SearchType {
        ALL,
        BY_ID
    }
}

/**
 * Petició per actualitzar una sessió existent.
 */
data class SessionServiceUpdateInfo(
    val sessionToken: String,
    val item: SessionService // Al servidor es diu "item" a UpdateInfo<T>
)

/**
 * Petició per eliminar/tancar una sessió.
 */
data class SessionServiceDeleteInfo(
    val sessionToken: String,
    val id: Long
)