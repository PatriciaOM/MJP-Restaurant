package com.example.mjprestaurant.model.dish

/**
 * Model que representa un plat del menú del restaurant.
 * Adaptat per coincidir amb el model Dish del servidor.
 *
 * @property id Identificador únic del plat
 * @property name Nom del plat (en anglès per coincidir amb servidor)
 * @property description Descripció del plat
 * @property price Preu actual del plat en euros (float per coincidir)
 * @property category Categoria a la que pertany el plat
 * @property available Indica si el plat està disponible per a comanda
 *
 * @see DishCategory
 * @see DishRequest
 *
 * @author Martin Muñoz Pozuelo
 */
data class Dish(
    val id: Int?,
    val name: String,
    val description: String,
    val price: Float,
    val category: DishCategory,
    val available: Boolean
) {
    /**
     * Verifica si el dish està disponible per a ser modificat/eliminat.
     *
     * @return true si el dish no està referenciat en cap comanda, sino false.
     *
     * @note Ara mateix sempre retorna true.
     */
    fun canBeModified(): Boolean{
        // TODO: Implementar la lògica quan el servidor estigui llest.
        return true
    }

    /**
     * Obté el preu formatat amb el símbol de l'euro.
     *
     * @return String amb el preu formatat.
     */
    fun getPriceFormatted(): String = "%.2f €".format(price)
}