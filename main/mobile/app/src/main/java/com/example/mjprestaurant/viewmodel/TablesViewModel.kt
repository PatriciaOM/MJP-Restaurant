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
 * - Mantenir la llista actual de taules
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
     * Es carrega automàticament quan es crida a loadTables().
     */
    val taules = mutableStateOf<List<TableStatus>>(emptyList())

    /**
     * Indica si hi ha una petició de càrrega de taules en curs.
     * Quan és true, es mostra un spinner a la interfície.
     */
    val isLoading = mutableStateOf(false)

    /**
     * Missatge d'error a mostrar a l'usuari en cas de fallada en carregar les taules.
     * És null quan no hi ha error.
     */
    val errorMessage = mutableStateOf<String?>(null)

    /**
     * Carrega la llista de taules des del servidor.
     *
     * Realitza les següents accions:
     * 1. Inicia l'estat de càrrega
     * 2. Neteja qualsevol error previ
     * 3. Crida al repositori per obtenir les taules
     * 4. Actualitza l'estat amb el resultat
     * 5. Maneja errors de xarxa o servidor
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
                        taules.value = body.tables ?: emptyList()
                    } else {
                        errorMessage.value = "Resposta del servidor buida"
                    }
                } else {
                    // Maneig d'errors HTTP
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
     * Útil per a proves o quan es vol forçar una actualització.
     *
     * @param token Token de sessió de l'usuari autenticat
     */
    fun refreshTables(token: String) {
        loadTables(token)
    }

    /**
     * Neteja el missatge d'error actual.
     * Útil quan l'usuari vol tornar a intentar després d'un error.
     */
    fun clearError() {
        errorMessage.value = null
    }
}