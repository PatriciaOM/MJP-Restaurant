package com.example.mjprestaurant.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjprestaurant.model.table.TableStatus
import com.example.mjprestaurant.network.AuthRepository
import kotlinx.coroutines.launch

/**
 * ViewModel per a la gestió de l'estat de les taules del restaurant.
 *
 * Aquesta classe s'encarrega de:
 * - Carregar la llista de taules des del servidor
 * - Gestionar l'estat de càrrega i errors
 * - Mantenir la llista actual de taules per a que altres pantalles (com TableDetail)
 * puguin consultar informació estàtica (ex: capacitat).
 *
 * @see TableStatus
 * @see AuthRepository
 *
 * @author Martín Muñoz Pozuelo
 */
class TablesViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    /**
     * Llista observable de totes les taules del restaurant.
     * Inicialitzada buida per evitar mostrar dades falses.
     */
    val taules = mutableStateOf<List<TableStatus>>(emptyList())

    /**
     * Indica si hi ha una petició de càrrega de taules en curs.
     */
    val isLoading = mutableStateOf(false)

    /**
     * Missatge d'error a mostrar a l'usuari.
     */
    val errorMessage = mutableStateOf<String?>(null)

    /**
     * Carrega la llista de taules des del servidor.
     *
     * @param token Token de sessió de l'usuari autenticat
     */
    fun loadTables(token: String) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = repository.getTableStatus(token)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        // Actualitzem la llista amb les dades reals del servidor
                        taules.value = body.tables ?: emptyList()
                    } else {
                        errorMessage.value = "Resposta del servidor buida"
                    }
                } else {
                    when (response.code()) {
                        401 -> errorMessage.value = "Sessió expirada. Torna a iniciar sessió"
                        500 -> errorMessage.value = "Error intern del servidor"
                        else -> errorMessage.value = "Error carregant taules: ${response.code()}"
                    }
                }
            } catch (e: Exception) {
                errorMessage.value = "No s'ha pogut connectar amb el servidor: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    /**
     * Actualitza manualment la llista de taules.
     * Buida la llista visualment abans de carregar per donar feedback de refresc.
     *
     * @param token Token de sessió de l'usuari autenticat
     */
    fun refreshTables(token: String) {
        taules.value = emptyList()
        loadTables(token)
    }

    /**
     * Neteja el missatge d'error actual.
     */
    fun clearError() {
        errorMessage.value = null
    }
}