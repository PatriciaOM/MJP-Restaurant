package com.example.mjprestaurant.model.session.response

import com.example.mjprestaurant.model.session.SessionService
import com.google.gson.annotations.SerializedName

/**
 * Resposta genèrica per a operacions de SessionService.
 * Mapeja la resposta del servidor que conté la llista "sessionServices".
 *
 * @property messageStatus Estat de la petició ("success", etc.)
 * @property sessionServices Llista de sessions retornades
 */
data class SessionServiceResponse(
    val messageStatus: String,
    @SerializedName("sessionServices")
    val sessionServices: List<SessionService>
)