package com.example.mjprestaurant.model.session

import com.google.gson.annotations.SerializedName

/**
 * Representa una sessió de servei en una taula (SessionService al servidor).
 * Conté tota la informació de l'estat actual de la taula.
 *
 * @property id ID únic de la sessió
 * @property idTable ID de la taula física
 * @property numTable Número visual de la taula
 * @property maxClients Capacitat màxima
 * @property waiterId ID del cambrer que ha obert la sessió
 * @property clients Nombre de comensals actuals
 * @property startDate Data d'inici (format YYYY-MM-DD)
 * @property endDate Data de fi
 * @property status Estat de la sessió (OPEN, CLOSED, PAID)
 * @property rating Valoració del servei
 * @property comment Comentaris addicionals
 *
 * @author Martin Muñoz Pozuelo
 */
data class SessionService(
    val id: Long? = null,
    val idTable: Long,
    val numTable: Int,
    val maxClients: Int,
    val waiterId: Int,
    val clients: Int,
    val startDate: String? = null, // Rebut com String des de LocalDate
    val endDate: String? = null,
    val status: SessionStatus,
    val rating: Int = 0,
    val comment: String? = null
)

/**
 * Estats possibles d'una sessió.
 */
enum class SessionStatus {
    OPEN,
    CLOSED,
    PAID
}