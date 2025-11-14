package com.example.mjprestaurant.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjprestaurant.model.dish.Dish
import com.example.mjprestaurant.model.dish.DishCategory
import com.example.mjprestaurant.model.dish.DishRequest
import com.example.mjprestaurant.network.AuthRepository
import kotlinx.coroutines.launch

/**
 * ViewModel per a la gestió de plats del restaurant.
 *
 * Aquesta classe s'encarrega de:
 * - Gestionar la llista de plats
 * - Controlar els estats de càrrega i errors
 * - Gestionar les operacions CRUD de plats
 * - Mantenir els filtres i cerques
 *
 * @see Dish
 * @see DishCategory
 * @see AuthRepository
 *
 * @author Martín Muñoz Pozuelo
 */
class DishViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    // Estats de dades
    val dishes = mutableStateOf<List<Dish>>(emptyList())
    val filteredDishes = mutableStateOf<List<Dish>>(emptyList())

    // Estats UI
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    // Filtres i cerca
    val categoryFilter = mutableStateOf<DishCategory?>(null)
    val searchText = mutableStateOf("")
    val selectedDish = mutableStateOf<Dish?>(null)

    // Operacions de càrrega de dades

    /**
     * Carrega tots els plats des del servidor.
     */
    fun loadDishes(token: String) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = repository.getDishes(token)

                if (response.isSuccessful) {
                    val dishResponse = response.body()
                    if (dishResponse != null && dishResponse.messageStatus.equals("success", ignoreCase = true)) {
                        dishes.value = dishResponse.dishes
                        applyFilters()
                    } else {
                        errorMessage.value = "Error carregant plats: ${dishResponse?.messageStatus}"
                    }
                } else {
                    errorMessage.value = "Error del servidor: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "No s'han pogut carregar els plats: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    // Operacions CRUD

    /**
     * Crea un nou dish al menú.
     */
    fun createDish(token: String, dishRequest: DishRequest): Boolean {
        val validation = dishRequest.validate()
        if (validation is DishRequest.ValidationResult.Error) {
            errorMessage.value = validation.message
            return false
        }

        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                // Convertir DishRequest a Dish per al servidor
                val newDish = Dish(
                    id = null,  // ← ENVIAR NULL para nuevos platos
                    name = dishRequest.name,
                    description = dishRequest.description,
                    price = dishRequest.price,
                    category = dishRequest.dishCategory,  // Usar dishCategory del request
                    available = dishRequest.available
                )

                val response = repository.createDish(token, newDish)

                if (response.isSuccessful) {
                    val dishResponse = response.body()
                    if (dishResponse != null && dishResponse.messageStatus.equals("success", ignoreCase = true)) {
                        // Recarregar tots els plats per obtenir l'actualització
                        loadDishes(token)
                    } else {
                        errorMessage.value = "Error creant plat: ${dishResponse?.messageStatus}"
                    }
                } else {
                    errorMessage.value = "Error del servidor: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "No s'ha pogut crear el plat: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }

        return true
    }


    /**
     * Actualitza un dish existent.
     */
    fun updateDish(token: String, dishId: Int, dishRequest: DishRequest): Boolean {
        val validation = dishRequest.validate()
        if (validation is DishRequest.ValidationResult.Error) {
            errorMessage.value = validation.message
            return false
        }

        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                // Convertir DishRequest a Dish per al servidor
                val updatedDish = Dish(
                    id = dishId,  // Para actualizar, usar el ID existente
                    name = dishRequest.name,
                    description = dishRequest.description,
                    price = dishRequest.price,
                    category = dishRequest.dishCategory,  // Usar dishCategory del request
                    available = dishRequest.available
                )

                val response = repository.updateDish(token, updatedDish)

                if (response.isSuccessful) {
                    val dishResponse = response.body()
                    if (dishResponse != null && dishResponse.messageStatus.equals("success", ignoreCase = true)) {
                        // Recarregar tots els plats per obtenir l'actualització
                        loadDishes(token)
                        clearSelection()
                    } else {
                        errorMessage.value = "Error actualitzant plat: ${dishResponse?.messageStatus}"
                    }
                } else {
                    errorMessage.value = "Error del servidor: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "No s'ha pogut actualitzar el plat: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }

        return true
    }

    /**
     * Elimina un dish del menú.
     */
    fun deleteDish(token: String, dishId: Int) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = repository.deleteDish(token, dishId.toLong())

                if (response.isSuccessful) {
                    val dishResponse = response.body()
                    if (dishResponse != null && dishResponse.messageStatus.equals("success", ignoreCase = true)) {
                        // Recarregar tots els plats per obtenir l'actualització
                        loadDishes(token)
                        clearSelection()
                    } else {
                        errorMessage.value = "Error eliminant plat: ${dishResponse?.messageStatus}"
                    }
                } else {
                    errorMessage.value = "Error del servidor: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "No s'ha pogut eliminar el plat: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }


    // Gestió de filtres i cerca

    /**
     * Canvia la dishCategory del filtre actual.
     */
    fun changeCategoryFilter(category: DishCategory?) {
        categoryFilter.value = category
        applyFilters()
    }

    /**
     * Canvia el text de cerca actual.
     */
    fun changeSearchText(text: String) {
        searchText.value = text
        applyFilters()
    }

    /**
     * Aplica els filtres actuals a la llista de plats.
     */
    private fun applyFilters() {
        var filtered = dishes.value

        // Filtrar per categoria
        categoryFilter.value?.let { category ->
            filtered = filtered.filter { it.category == category }
        }

        // Filtrar per text de cerca
        if (searchText.value.isNotBlank()) {
            val text = searchText.value.lowercase()
            filtered = filtered.filter { dish ->
                dish.name.lowercase().contains(text) ||
                        dish.description.lowercase().contains(text)
            }
        }

        filteredDishes.value = filtered
    }

    // Gestió de selecció

    /**
     * Selecciona un dish per a operacions d'edició o eliminació.
     */
    fun selectDish(dish: Dish) {
        selectedDish.value = dish
    }

    /**
     * Neteja la selecció actual de dish.
     */
    fun clearSelection() {
        selectedDish.value = null
    }

    // Gestió d'errors

    /**
     * Neteja el missatge d'error actual.
     */
    fun clearError() {
        errorMessage.value = null
    }
}