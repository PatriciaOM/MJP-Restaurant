package com.example.mjprestaurant.network

import com.example.mjprestaurant.model.auth.LoginRequest
import com.example.mjprestaurant.model.auth.LoginResponse
import com.example.mjprestaurant.model.auth.LogoutRequest
import com.example.mjprestaurant.model.table.TableStatusRequest
import com.example.mjprestaurant.model.table.TableStatusResponse
import com.example.mjprestaurant.model.dish.DishCategory
import com.example.mjprestaurant.model.dish.Dish
import com.example.mjprestaurant.model.dish.request.DishCreateInfo
import com.example.mjprestaurant.model.dish.request.DishDeleteInfo
import com.example.mjprestaurant.model.dish.request.DishGetInfo
import com.example.mjprestaurant.model.dish.request.DishUpdateInfo
import com.example.mjprestaurant.model.dish.DishResponse
import retrofit2.Response
import kotlinx.coroutines.delay


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
 * @see Dish
 * @see DishViewModel
 *
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

    /**
     * Obté l'estat actual de totes les taules del restaurant.
     *
     * @param token Token de sessió de l'usuari autenticat
     * @return Response amb la llista d'estats
     *
     * @throws Exception Si hi ha problemes de xarxa o el servidor no respon
     *
     * @sample getTableStatus("session_token_123")
     */
    suspend fun getTableStatus(token: String): Response<TableStatusResponse> {
        val request = TableStatusRequest(sessionToken = token)
        return RetrofitInstance.api.getTableStatus(request)
    }

    /**
     * Obté tots els plats del menú des del servidor.
     *
     * @param token Token de sessió  de l'usuari autenticat
     * @return Response amb la llista de tots els plats
     *
     */
    suspend fun getDishes(token: String): Response<DishResponse> {
        val request = DishGetInfo(sessionToken = token)
        return RetrofitInstance.api.getDishes(request)
    }

    /**
     * Crea un nou plat al menú.
     *
     * @param token Token de sessió de l'usuari autenticat
     * @param dish Plat a crear
     * @return Response amb el plat creat
     */
    suspend fun createDish(token: String, dish: Dish): Response<DishResponse> {
        val request = DishCreateInfo(sessionToken = token, newEntry = dish)
        return RetrofitInstance.api.createDish(request)
    }

    /**
     * Actualitza un plat existent.
     *
     * @param token Token de sessió de l'usuari autenticat
     * @param dish Plat actualitzat
     * @return Response amb el plat actualitzat
     */
    suspend fun updateDish(token: String, dish: Dish): Response<DishResponse> {
        val request = DishUpdateInfo(sessionToken = token, newEntry = dish)
        return RetrofitInstance.api.updateDish(request)
    }

    /**
     * Elimina un plat del menú.
     *
     * @param token Token de sessió de l'usuari autenticat
     * @param dishId ID del plat a eliminar
     * @return Response indicant l'èxit de l'operació
     */
    suspend fun deleteDish(token: String, dishId: Long): Response<DishResponse> {
        val request = DishDeleteInfo(sessionToken = token, id = dishId)
        return RetrofitInstance.api.deleteDish(request)
    }

}