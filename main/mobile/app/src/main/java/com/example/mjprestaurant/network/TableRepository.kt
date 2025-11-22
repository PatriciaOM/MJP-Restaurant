package com.example.mjprestaurant.network

import com.example.mjprestaurant.model.dish.Dish
import com.example.mjprestaurant.model.order.Order
import com.example.mjprestaurant.model.order.OrderItem
import com.example.mjprestaurant.model.order.request.OrderCreateInfo
import com.example.mjprestaurant.model.order.request.OrderGetInfo
import com.example.mjprestaurant.model.order.request.OrderItemCreateInfo
import com.example.mjprestaurant.model.order.request.OrderUpdateInfo
import com.example.mjprestaurant.model.order.request.OrderDeleteInfo
import com.example.mjprestaurant.model.order.response.OrderItemResponse
import com.example.mjprestaurant.model.order.response.OrderResponse
import com.example.mjprestaurant.model.session.SessionService
import com.example.mjprestaurant.model.session.request.SessionServiceCreateInfo
import com.example.mjprestaurant.model.session.request.SessionServiceDeleteInfo
import com.example.mjprestaurant.model.session.request.SessionServiceGetInfo
import com.example.mjprestaurant.model.session.request.SessionServiceUpdateInfo
import com.example.mjprestaurant.model.session.response.SessionServiceResponse
import retrofit2.Response

/**
 * Repositori encarregat de la gestió operativa de les taules i comandes.
 *
 * Aquesta classe gestiona:
 * - La creació i recuperació de sessions (Obrir/Veure taula).
 * - La gestió de les comandes (Orders) associades a una sessió.
 * - L'addició de línies de comanda (OrderItems) amb els plats demanats.
 *
 * Separem aquesta lògica de l'AuthRepository per mantenir el codi net i modular.
 *
 * @see ApiService
 * @see RetrofitInstance
 * @see SessionService
 * @see Order
 * @see OrderItem
 *
 * @author Martin Muñoz Pozuelo
 */
class TableRepository {

    // --- GESTIÓ DE SESSIONS (OBRIR/TANCAR TAULES) ---

    /**
     * Obre una nova taula (Crea una sessió).
     *
     * @param token Token de sessió de l'usuari.
     * @param sessionService Objecte amb les dades inicials de la sessió (taula, cambrer, etc.).
     * @return Response amb la sessió creada.
     */
    suspend fun createSession(token: String, sessionService: SessionService): Response<SessionServiceResponse> {
        val request = SessionServiceCreateInfo(sessionToken = token, newEntry = sessionService)
        return RetrofitInstance.api.createSession(request)
    }

    /**
     * Obté totes les sessions actives.
     * S'utilitza per saber quina sessió correspon a una taula ocupada.
     *
     * @param token Token de sessió de l'usuari.
     * @return Response amb la llista de sessions.
     */
    suspend fun getSessions(token: String): Response<SessionServiceResponse> {
        val request = SessionServiceGetInfo(
            sessionToken = token,
            searchType = SessionServiceGetInfo.SearchType.ALL
        )
        return RetrofitInstance.api.getSessions(request)
    }

    /**
     * Actualitza una sessió existent.
     */
    suspend fun updateSession(token: String, sessionService: SessionService): Response<SessionServiceResponse> {
        val request = SessionServiceUpdateInfo(sessionToken = token, item = sessionService)
        return RetrofitInstance.api.updateSession(request)
    }

    /**
     * Elimina o tanca una sessió.
     */
    suspend fun deleteSession(token: String, sessionId: Long): Response<SessionServiceResponse> {
        val request = SessionServiceDeleteInfo(sessionToken = token, id = sessionId)
        return RetrofitInstance.api.deleteSession(request)
    }


    // --- GESTIÓ DE COMANDES (ORDERS) ---

    /**
     * Crea una nova comanda per a una sessió (Capçalera).
     *
     * @param token Token de sessió de l'usuari.
     * @param order Objecte comanda a crear.
     * @return Response amb la comanda creada.
     */
    suspend fun createOrder(token: String, order: Order): Response<OrderResponse> {
        val request = OrderCreateInfo(sessionToken = token, newEntry = order)
        return RetrofitInstance.api.createOrder(request)
    }

    /**
     * Obté la comanda associada a una sessió específica.
     * Utilitza el tipus de cerca BY_SESSION_SERVICE definit al backend.
     *
     * @param token Token de sessió de l'usuari.
     * @param sessionId ID de la sessió de la qual volem la comanda.
     * @return Response amb la comanda trobada.
     */
    suspend fun getOrderBySession(token: String, sessionId: Long): Response<OrderResponse> {
        val request = OrderGetInfo(
            sessionToken = token,
            searchType = OrderGetInfo.SearchType.BY_SESSION_SERVICE,
            id = sessionId
        )
        return RetrofitInstance.api.getOrders(request)
    }


    // --- GESTIÓ DE LÍNIES DE COMANDA (ORDER ITEMS) ---

    /**
     * Afegeix un plat a la comanda actual.
     *
     * Construeix l'objecte OrderItem amb tota la informació del plat (preu, nom, categoria, descripció, idDish)
     * tal com requereix el servidor.
     *
     * @param token Token de sessió de l'usuari.
     * @param orderId ID de la comanda on afegir el plat.
     * @param dish Objecte Dish complet.
     * @param quantity Quantitat a demanar.
     * @return Response amb la línia creada.
     */
    suspend fun addDishToOrder(token: String, orderId: Long, dish: Dish, quantity: Int): Response<OrderItemResponse> {
        // Creem l'objecte segons els nous camps del servidor Java
        val newItem = OrderItem(
            idOrder = orderId,
            idDish = dish.id ?: 0L, // Necessitem l'ID del plat
            amount = quantity,
            price = dish.price,
            name = dish.name,      // El nom va al camp 'name'
            description = dish.description, // La descripció al camp 'description'
            category = dish.category
        )

        val request = OrderItemCreateInfo(sessionToken = token, newEntry = newItem)
        return RetrofitInstance.api.addOrderItem(request)
    }
}