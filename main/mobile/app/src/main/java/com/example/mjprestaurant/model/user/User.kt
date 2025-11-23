package com.example.mjprestaurant.model.user

/**
 *Model de dades que representa un usuari a l'aplicació.
 *
 * Aquesta classe s'utilitza per a :
 *  - Emmagatzemar informació bàsica de l'usuari autenticat.
 *  - Mostrar informació d'usuari a l'interficie
 *  - Gestionar permisos basats en el rol
 *
 *  @property username nom únic identificador d'usuari
 *  @property role rol de l'usuari que determina permisos ("user", "admin")
 *
 *  @see com.example.mjprestaurant.model.auth.LoginResponse
 *  @see LoginViewModel
 *
 *  @author Martin Muñoz Pozuelo
 */
data class User(
        val username: String,
        val role: String
    )