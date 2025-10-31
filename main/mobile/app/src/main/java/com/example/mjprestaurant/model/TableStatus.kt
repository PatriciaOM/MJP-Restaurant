package com.example.mjprestaurant.model

/**
 * Estat actual de la taula del restaurant.
 *
 * @property id Identificador únic de la taula
 * @property maxClients Capacitat màxima de comensales
 * @property clientsAmount número actual de comensals a la taula
 *
 * @see TableStatusResponse
 */
data class TableStatus(
    val id: Int,
    val maxClients: Int,
    val clientsAmount: Int
) {
    /**
     * Indica si la taula està totalment ocupada.
     *
     * @return true si clientsAmount > 0, false si està lliure
     */
    fun estaOcupada(): Boolean = clientsAmount > 0

    /**
     * Obté l'estat llegible de la taula
     *
     * @return "Ocupada" si te comensals, Lliure si està disponible
     */
    fun getStatusText(): String = if (estaOcupada()) "Ocupada" else "Lliure"

    /**
     * Obté la capacitat de la taula
     *
     * @return String amb format "X/Y" on X= comensals actuals, Y= capacitat màxima
     */
    fun getCapacityText(): String = "$clientsAmount/$maxClients"

}