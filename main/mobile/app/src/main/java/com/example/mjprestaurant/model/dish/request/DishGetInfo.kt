package com.example.mjprestaurant.model.dish.request

/**
 * Petició per obtenir plats.
 * Adaptada al servidor.
 *
 * @property sessionToken Token de sessió de l'usuari
 * @property searchType Tipus de cerca (ALL per defecte)
 *
 * @see Dish
 * @see DishGetResponse
 *
 * @author Martin Muñoz Pozuelo
 */
data class DishGetInfo(
    val sessionToken: String,
    val searchType: SearchType = SearchType.ALL
) {
    enum class SearchType {
        ALL,
        BY_ID,
        BY_NAME
    }
}