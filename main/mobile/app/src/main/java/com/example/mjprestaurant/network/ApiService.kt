package com.example.mjprestaurant.network

import com.example.mjprestaurant.model.auth.LoginRequest
import com.example.mjprestaurant.model.auth.LoginResponse
import com.example.mjprestaurant.model.auth.LogoutRequest
import com.example.mjprestaurant.model.table.TableStatusRequest
import com.example.mjprestaurant.model.table.TableStatusResponse
import com.example.mjprestaurant.model.dish.DishResponse
import com.example.mjprestaurant.model.dish.request.DishCreateInfo
import com.example.mjprestaurant.model.dish.request.DishDeleteInfo
import com.example.mjprestaurant.model.dish.request.DishGetInfo
import com.example.mjprestaurant.model.dish.request.DishUpdateInfo
// Imports de Sessió
import com.example.mjprestaurant.model.session.request.SessionServiceCreateInfo
import com.example.mjprestaurant.model.session.request.SessionServiceGetInfo
import com.example.mjprestaurant.model.session.request.SessionServiceUpdateInfo
import com.example.mjprestaurant.model.session.request.SessionServiceDeleteInfo
import com.example.mjprestaurant.model.session.response.SessionServiceResponse
// Imports de Comanda (Order)
import com.example.mjprestaurant.model.order.request.OrderCreateInfo
import com.example.mjprestaurant.model.order.request.OrderGetInfo
import com.example.mjprestaurant.model.order.request.OrderUpdateInfo
import com.example.mjprestaurant.model.order.request.OrderDeleteInfo
import com.example.mjprestaurant.model.order.response.OrderResponse
// Imports de Línies de Comanda (OrderItem) <-- AQUESTS FALTAVEN
import com.example.mjprestaurant.model.order.request.OrderItemCreateInfo
import com.example.mjprestaurant.model.order.response.OrderItemResponse

import retrofit2.Response
import retrofit2.http.*

/**
 * Interficie que defineix els endpoints de l'API REST.
 * Inclou autenticació, gestió de taules, plats, sessions i comandes.
 *
 * @author Martin Muñoz Pozuelo
 */
interface ApiService {

    // --- AUTENTICACIÓ ---

    @POST("login")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    @POST("logout")
    suspend fun logout(@Body body: LogoutRequest): Response<Unit>

    // --- ESTAT DE TAULES (VISUALITZACIÓ) ---

    @POST("table-status")
    suspend fun getTableStatus(@Body body: TableStatusRequest): Response<TableStatusResponse>

    // --- GESTIÓ DE PLATS (MENÚ) ---

    @POST("dish/create")
    suspend fun createDish(@Body body: DishCreateInfo): Response<DishResponse>

    @POST("dish/get")
    suspend fun getDishes(@Body body: DishGetInfo): Response<DishResponse>

    @POST("dish/update")
    suspend fun updateDish(@Body body: DishUpdateInfo): Response<DishResponse>

    @POST("dish/delete")
    suspend fun deleteDish(@Body body: DishDeleteInfo): Response<DishResponse>


    // --- GESTIÓ DE SESSIONS (OBRIR/TANCAR TAULES) ---

    @POST("session-service/create")
    suspend fun createSession(@Body body: SessionServiceCreateInfo): Response<SessionServiceResponse>

    @POST("session-service/get")
    suspend fun getSessions(@Body body: SessionServiceGetInfo): Response<SessionServiceResponse>

    @POST("session-service/update")
    suspend fun updateSession(@Body body: SessionServiceUpdateInfo): Response<SessionServiceResponse>

    @POST("session-service/delete")
    suspend fun deleteSession(@Body body: SessionServiceDeleteInfo): Response<SessionServiceResponse>


    // --- GESTIÓ DE COMANDES (ORDERS - CAPÇALERA) ---

    @POST("order/create")
    suspend fun createOrder(@Body body: OrderCreateInfo): Response<OrderResponse>

    @POST("order/get")
    suspend fun getOrders(@Body body: OrderGetInfo): Response<OrderResponse>

    @POST("order/update")
    suspend fun updateOrder(@Body body: OrderUpdateInfo): Response<OrderResponse>

    @POST("order/delete")
    suspend fun deleteOrder(@Body body: OrderDeleteInfo): Response<OrderResponse>


    // --- GESTIÓ DE LÍNIES DE COMANDA (ORDER ITEMS - PLATS) ---

    /**
     * Afegeix un plat a una comanda existent.
     * Aquest endpoint permet afegir ítems de forma acumulativa.
     */
    @POST("order-item/create")
    suspend fun addOrderItem(@Body body: OrderItemCreateInfo): Response<OrderItemResponse>

}