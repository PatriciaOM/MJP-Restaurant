package com.example.mjprestaurant.network

import android.util.Log
import com.example.mjprestaurant.model.LoginRequest
import com.example.mjprestaurant.model.LoginResponse
import com.example.mjprestaurant.model.LogoutRequest
import retrofit2.Response

/**
 * Repositori d'autenticació que actua com intermediari entre el ViewModel i l'API.
 *
 * Aquesta classe s'encarrega de:
 * - Preparar les peticions d'autenticació.
 * - Trucades als serveis corresponents.
 * - Manejar la comunicació amb Retrofit.
 *
 * @see LoginViewModel
 * @see ApiService
 * @see RetrofitInstance
 *
 * @author Martin Muñoz Pozuelo
 */
class AuthRepository {

    /**
     * Realitza el procés d'autenticació d'un usuari
     *
     * @param username nom d'usuari per autenticar.
     * @param password contrasenya de l'usuari
     * @param role Rol d'usuari ("user" o "admin")
     * return Response amb el resultat del login, afegint token i rol.
     *
     * @throws Exception Si hi ha problemes de xarxa o servidor.
     *
     * @sample login("usuari", "contrasenya123", "user")
     */
    suspend fun login(username: String, password: String, role: String): Response<LoginResponse> {
        val request = LoginRequest(username, password, role)
        return RetrofitInstance.api.login(request)
    }

    /**
     * Realitza el tancament de sessió de l'usuari actual
     *
     * @param token Token de sessió a invalidar al servidor.
     * @return Response buit indicant l'èxit.
     *
     * @throws Exception si hi ha problemes de xarxa o el servidor no respon.
     *
     * @sample logout("session_token_123")
     */
    suspend fun logout(token: String): Response<Unit> {
        val request = LogoutRequest(sessionToken = token)
        return RetrofitInstance.api.logout(request)
    }
}