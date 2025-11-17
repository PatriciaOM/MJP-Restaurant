package com.example.mjprestaurant.network

import com.example.mjprestaurant.model.auth.LoginRequest
import com.example.mjprestaurant.model.auth.LoginResponse
import com.example.mjprestaurant.model.auth.LogoutRequest
import com.example.mjprestaurant.model.table.TableStatusRequest
import com.example.mjprestaurant.model.table.TableStatusResponse
import com.example.mjprestaurant.model.dish.Dish
import com.example.mjprestaurant.model.dish.DishRequest
import com.example.mjprestaurant.model.dish.request.DishGetInfo
import com.example.mjprestaurant.model.dish.request.DishDeleteInfo
import com.example.mjprestaurant.model.dish.request.DishUpdateInfo
import com.example.mjprestaurant.model.dish.request.DishCreateInfo
import com.example.mjprestaurant.model.dish.DishResponse
import com.example.mjprestaurant.model.session.request.SessionServiceCreateInfo
import com.example.mjprestaurant.model.session.request.SessionServiceGetInfo
import com.example.mjprestaurant.model.session.request.SessionServiceUpdateInfo
import com.example.mjprestaurant.model.session.request.SessionServiceDeleteInfo
import com.example.mjprestaurant.model.session.response.SessionServiceResponse
import com.example.mjprestaurant.model.order.request.OrderCreateInfo
import com.example.mjprestaurant.model.order.request.OrderGetInfo
import com.example.mjprestaurant.model.order.request.OrderUpdateInfo
import com.example.mjprestaurant.model.order.request.OrderDeleteInfo
import com.example.mjprestaurant.model.order.response.OrderResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Interficie que defineix els endpoints de l'API REST per a autenticació
 *
 * Utilitza Retrofit per a les comunicacions HTTP i Gson per a la serialització.
 *
 * @see AuthRepository
 * @see RetrofitInstance
 * @see Dish
 * @see DishRequest
 *
 * @author Martin Muñoz Pozuelo
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
     * @throws IOException En cas de problemes de xarxa
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
     * @throws IOException en cas de problemes de xarxa.
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

    /**
     * Endpoit per crear un nou plat al menú
     *
     * @param body Objecte DishCreateInfo amb les dades del nou plat
     * @return Response amb DishCreateResponse que conté el plat creat
     *
     * @sample DishCreateInfo(sessionToken, Dish(...))
     * @sample DishCreateResponse("success", listOf(Dish...)))
     *
     * @throws IOException En cas de problemes de xarxa
     */
    @POST("dish/create")
    suspend fun createDish(@Body body: DishCreateInfo): Response <DishResponse>

    /**
     * Endpoint per obtenir tots els plats del menú
     *
     * @param body Objecte DishGetInfo amb el token de sessió
     * @return Response amb DishGetResponse que conté la llista de plats
     *
     * @sample DishGetInfo("session_token_123")
     * @sample DishGetResponse("success", listOf(Dish(...), Dish(...)))
     *
     * @throws IOException En cas de problemes de xarxa
     */
    @POST("dish/get")
    suspend fun getDishes(@Body body: DishGetInfo): Response<DishResponse>

    /**
     * Endpoint per actualitzar un plat existent.
     *
     * @param body Objecte DishUpdateInfo amb les dades actualitzades
     * @return Response amb DishUpdateResponse que conté el plat actualitzat
     *
     * @sample DishUpdateInfo("session_token_123", Dish(...))
     * @sample DishUpdateResponse("success", lisOf(Dish(...))
     *
     * @throws IOException En cas de problemees de xarxa
     */
    @POST("dish/update")
    suspend fun updateDish(@Body body: DishUpdateInfo): Response<DishResponse>

    /**
     * Endpoint per esborrar un plat del menú
     *
     * @param body Objecte DishDeleteInfo amb l'ID del plat a eliminar
     * @return Response amb DishDeleteResponse indicant l'èxit.
     *
     * @sample DishDeleteInfo("session_token_123", 1L)
     * @sample DishDeleteResponse("success", emptyList())
     *
     * @throws IOEXception En cas de problemes de xarxa
     */
    @POST("dish/delete")
    suspend fun deleteDish(@Body body: DishDeleteInfo): Response<DishResponse>

    /**
     * Crea una nova sessió (Obre una taula).
     */
    @POST("session-service/create")
    suspend fun createSession(@Body body: SessionServiceCreateInfo): Response<SessionServiceResponse>

    /**
     * Obté informació de sessions (per saber quina està activa a una taula).
     */
    @POST("session-service/get")
    suspend fun getSessions(@Body body: SessionServiceGetInfo): Response<SessionServiceResponse>

    /**
     * Actualitza una sessió existent.
     */
    @POST("session-service/update")
    suspend fun updateSession(@Body body: SessionServiceUpdateInfo): Response<SessionServiceResponse>

    /**
     * Elimina o tanca una sessió.
     */
    @POST("session-service/delete")
    suspend fun deleteSession(@Body body: SessionServiceDeleteInfo): Response<SessionServiceResponse>


    // --- GESTIÓ DE COMANDES (ORDERS) ---

    /**
     * Crea una nova comanda (capçalera).
     */
    @POST("order/create")
    suspend fun createOrder(@Body body: OrderCreateInfo): Response<OrderResponse>

    /**
     * Obté comandes.
     * Farem servir el SearchType.BY_SESSION_SERVICE per trobar la comanda d'una taula.
     */
    @POST("order/get")
    suspend fun getOrders(@Body body: OrderGetInfo): Response<OrderResponse>

    /**
     * Actualitza l'estat d'una comanda.
     */
    @POST("order/update")
    suspend fun updateOrder(@Body body: OrderUpdateInfo): Response<OrderResponse>

    /**
     * Elimina una comanda.
     */
    @POST("order/delete")
    suspend fun deleteOrder(@Body body: OrderDeleteInfo): Response<OrderResponse>
}