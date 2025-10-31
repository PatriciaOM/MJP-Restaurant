package com.example.mjprestaurant.network

import com.example.mjprestaurant.model.LoginRequest
import com.example.mjprestaurant.model.LoginResponse
import com.example.mjprestaurant.model.LogoutRequest
import com.example.mjprestaurant.model.TableStatusRequest
import com.example.mjprestaurant.model.TableStatusResponse
import com.example.mjprestaurant.model.User
import retrofit2.Response
import retrofit2.http.*

/**
 * Interficie que defineix els endpoints de l'API REST per a autenticació
 *
 * Utilitza Retrofit per a les comunicacions HTTP i Gson per a la serialització.
 *
 * @see AuthRepository
 * @see RetrofitInstance
 */
interface  ApiService {

    /**
     * Endpoit per autenticació d'usuari
     *
     * @param body Objecte LoginRequest amb credencials d'usuari
     * @return Response amb LoginResponse en cas exitos o error HTTP
     *
     * @sample LoginRequest("user", "password", "user")
     * @sample LoginResponse("session_token_123", "user")
     *
     * @throws IoException En cas de problemes de xarxa
     */
    @POST("login")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    /**
     * Endpoint per a tancar sessió d'usuari
     *
     * @param body Objecte LogoutRequest amb token de sessió
     * @return Response buit amb codi 200 en cas d'exit
     *
     * @sample LogoutRequest("session_token_123")
     *
     * @throws IoException en cas de problemes de xarxa.
     */
    @POST("logout")
    suspend fun logout(@Body body: LogoutRequest): Response<Unit>

    /**
     * Endpoint per a obtenir l'estat de les taules del restaurant.
     *
     * @param body Objecte TableStatusRequest amb token de sessió
     * @return Response amb TableStatusResponse que conté la llista de Taules
     *
     * @sample TableStatusRequest("session_token_123")
     * @sample TableStatusResponse(taules =[TableStatus(1,6,4), TableStatus(2,4,0)])
     *
     * @throws IOException En cas de problema de xarxa
     */
    @POST("table-status")
    suspend fun getTableStatus(@Body body: TableStatusRequest): Response<TableStatusResponse>

}