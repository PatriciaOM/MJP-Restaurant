package com.example.mjprestaurant.network

import com.example.mjprestaurant.model.session.SessionService
import com.example.mjprestaurant.model.session.request.SessionServiceCreateInfo
import com.example.mjprestaurant.model.session.request.SessionServiceGetInfo
import com.example.mjprestaurant.model.session.response.SessionServiceResponse
import com.example.mjprestaurant.model.order.Order
import com.example.mjprestaurant.model.order.request.OrderCreateInfo
import com.example.mjprestaurant.model.order.request.OrderGetInfo
import com.example.mjprestaurant.model.order.response.OrderResponse
import retrofit2.Response

/**
 * Repositori encarregat de la gestió operativa de les taules i comandes.
 *
 * Aquesta classe gestiona:
 * - La creació i recuperació de sessions (Obrir/Veure taula).
 * - La gestió de les comandes associades a una sessió.
 *
 * Separem aquesta lògica de l'AuthRepository per mantenir el codi net.
 *
 * @see ApiService
 * @see RetrofitInstance
 *
 * @author Martin Muñoz Pozuelo
 */
class TableRepository {


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
     *
     * S'utilitza per saber quina sessió correspon a una taula ocupada.
     *
     * @param token Token de sessió de l'usuari.
     * @return Response amb la llista de sessions.
     */
    suspend fun getSessions(token: String): Response<SessionServiceResponse> {
        // Demanem totes (ALL) i filtrarem al ViewModel per trobar la de la taula específica
        val request = SessionServiceGetInfo(
            sessionToken = token,
            searchType = SessionServiceGetInfo.SearchType.ALL
        )
        return RetrofitInstance.api.getSessions(request)
    }

    // --- GESTIÓ DE COMANDES ---

    /**
     * Crea una nova comanda per a una sessió.
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
     *
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
}