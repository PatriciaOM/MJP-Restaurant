package com.example.mjprestaurant.model.dish

/**
 * Categories de plats dispnibles
 *
 * @see Dish
 * @see DishRequest
 *
 * @author Martin Muñoz Pozuelo
 */
enum class DishCategory {

    APPETIZER,
    MAIN,
    DESSERT,
    DRINK,
    OTHER;

    fun getDisplayName(): String = when(this) {
        APPETIZER -> "Entrant"
        MAIN -> "Principal"
        DESSERT -> "Postre"
        DRINK -> "Beguda"
        OTHER -> "Altres"
    }

    /**
     * Converteix des del enum del servidor.
     */
    companion object {
        fun fromString(value: String): DishCategory {
            return when(value.uppercase()) {
                "APPETIZER" -> APPETIZER
                "MAIN" -> MAIN
                "DESSERT" -> DESSERT
                "DRINK" -> DRINK
                else -> OTHER
            }
        }
    }
}