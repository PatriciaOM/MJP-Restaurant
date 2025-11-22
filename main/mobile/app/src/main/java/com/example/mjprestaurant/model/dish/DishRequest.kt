package com.example.mjprestaurant.model.dish

/**
 * Petició per crear o actualitzar un plat.
 * Adaptada per coincidir amb les peticions del servidor.
 *
 * @property name Nom del plat (en anglès)
 * @property description Descripció del plat
 * @property price Preu del plat (float)
 * @property category Categoria del plat
 * @property available Estat de disponibilitat del plat
 *
 * @see Dish
 * @see DishCategory
 *
 * @author Martin Muñoz Pozuelo
 */
data class DishRequest(
    val name: String,
    val description: String,
    val price: Float,
    val dishCategory: DishCategory,
    val available: Boolean = true
) {

    /**
     * Valida si les dades del plat són correctes.
     *
     * @return ValidationResult amb l'estat de la validació
     */
    fun validate(): ValidationResult {
        return when {
            name.isBlank() -> ValidationResult.Error("El nom no pot estar buit")
            description.isBlank() -> ValidationResult.Error("La descripció no pot estar buida")
            price <= 0 -> ValidationResult.Error("El preu ha de ser major a 0")
            else -> ValidationResult.Success
        }
    }

    /**
     * Resultat de la validació d'un DishRequest.
     *
     * @see DishRequest
     */
    sealed class ValidationResult {
        object Success : ValidationResult()
        data class Error(val message: String) : ValidationResult()
    }


}