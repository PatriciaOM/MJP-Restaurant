package com.example.mjprestaurant.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjprestaurant.model.order.Order
import com.example.mjprestaurant.model.order.OrderStatus
import com.example.mjprestaurant.model.session.SessionService
import com.example.mjprestaurant.model.session.SessionStatus
import com.example.mjprestaurant.network.TableRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel encarregat de la lògica de negoci i estat d'una sessió de taula.
 *
 * Aquesta classe gestiona el cicle de vida operatiu d'una taula específica.
 * Inclou lògica d'autocorrecció: si una sessió no té comanda, la crea.
 *
 * @author Martin Muñoz Pozuelo
 */
class TableSessionViewModel(
    private val repository: TableRepository = TableRepository()
) : ViewModel() {

    val currentSession = mutableStateOf<SessionService?>(null)
    val currentOrder = mutableStateOf<Order?>(null)
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    /**
     * Inicialitza la pantalla.
     */
    fun loadTableSession(token: String, tableId: Long) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = repository.getSessions(token)

                if (response.isSuccessful) {
                    val allSessions = response.body()?.sessionServices ?: emptyList()
                    val activeSession = allSessions.find { session ->
                        session.idTable == tableId && session.status == SessionStatus.OPEN
                    }

                    if (activeSession != null) {
                        currentSession.value = activeSession
                        // Intentem carregar la comanda
                        loadOrder(token, activeSession.id!!)
                    } else {
                        isLoading.value = false // Taula lliure
                    }
                } else {
                    errorMessage.value = "Error servidor: ${response.code()}"
                    isLoading.value = false
                }
            } catch (e: Exception) {
                errorMessage.value = "Error de connexió: ${e.message}"
                isLoading.value = false
            }
        }
    }

    /**
     * Obre taula i crea comanda inicial.
     */
    fun openTable(token: String, tableId: Long, diners: Int) {
        viewModelScope.launch {
            isLoading.value = true

            try {
                val newSession = SessionService(
                    idTable = tableId,
                    numTable = tableId.toInt(),
                    maxClients = 4,
                    waiterId = 1,
                    clients = diners,
                    startDate = LocalDate.now().toString(),
                    status = SessionStatus.OPEN
                )

                val response = repository.createSession(token, newSession)
                val body = response.body()
                val hasData = !body?.sessionServices.isNullOrEmpty()

                if (response.isSuccessful && hasData) {
                    val createdSession = body?.sessionServices?.firstOrNull()
                    if (createdSession != null) {
                        currentSession.value = createdSession
                        createInitialOrder(token, createdSession.id!!)
                    }
                } else {
                    errorMessage.value = "Error creant sessió: ${response.code()}"
                    isLoading.value = false
                }
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
                isLoading.value = false
            }
        }
    }

    /**
     * CORREGIT: Carrega la comanda, i SI NO EXISTEIX, LA CREA.
     */
    private fun loadOrder(token: String, sessionId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getOrderBySession(token, sessionId)
                val body = response.body()

                // Si tenim èxit i la llista NO és buida -> Tenim comanda
                if (response.isSuccessful && !body?.items.isNullOrEmpty()) {
                    currentOrder.value = body?.items?.firstOrNull()
                    isLoading.value = false // Tot correcte, acabem
                } else {
                    // Si la resposta és OK però la llista és buida -> LA CREEM ARA MATEIX
                    // Això soluciona el teu problema "Creant comanda..." etern
                    createInitialOrder(token, sessionId)
                }
            } catch (e: Exception) {
                errorMessage.value = "Error carregant ordre: ${e.message}"
                isLoading.value = false
            }
        }
    }

    /**
     * Crea la comanda al servidor.
     */
    private fun createInitialOrder(token: String, sessionId: Long) {
        viewModelScope.launch {
            try {
                val newOrder = Order(
                    idSessionService = sessionId,
                    date = LocalDate.now().toString(),
                    state = OrderStatus.OPEN
                )

                val response = repository.createOrder(token, newOrder)
                val body = response.body()
                val hasData = !body?.items.isNullOrEmpty()

                if (response.isSuccessful && hasData) {
                    currentOrder.value = body?.items?.firstOrNull()
                } else {
                    errorMessage.value = "Error creant comanda al servidor"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error xarxa ordre: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}