package com.example.mjprestaurant.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjprestaurant.model.dish.Dish
import com.example.mjprestaurant.model.order.Order
import com.example.mjprestaurant.model.order.OrderItem
import com.example.mjprestaurant.model.order.OrderStatus
import com.example.mjprestaurant.model.session.SessionService
import com.example.mjprestaurant.model.session.SessionStatus
import com.example.mjprestaurant.network.TableRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ViewModel encarregat de la lògica de negoci i estat d'una sessió de taula.
 *
 * Aquesta classe gestiona el cicle de vida operatiu d'una taula específica:
 * - Comprovar l'estat actual (Lliure vs Ocupada).
 * - Obrir una nova sessió (Create Session) assignant comensals.
 * - Gestionar la creació i recuperació de la comanda activa (Order).
 * - Gestionar el carret local i l'enviament de plats.
 *
 * @author Martin Muñoz Pozuelo
 */
class TableSessionViewModel(
    private val repository: TableRepository = TableRepository()
) : ViewModel() {

    // --- ESTATS DE DADES ---
    val currentSession = mutableStateOf<SessionService?>(null)
    val currentOrder = mutableStateOf<Order?>(null)

    // --- CARRET DE COMANDA (LOCAL) ---
    val cartItems = mutableStateListOf<Dish>()
    val sentItems = mutableStateListOf<OrderItem>()

    // --- ESTATS DE UI ---
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    /**
     * Funció auxiliar per obtenir la data actual en format ISO-8601.
     * Utilitzem SimpleDateFormat per compatibilitat amb versions antigues d'Android.
     */
    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    /**
     * Inicialitza la pantalla carregant l'estat actual de la taula.
     */
    fun loadTableSession(token: String, tableId: Long) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            sentItems.clear()

            try {
                val response = repository.getSessions(token)
                if (response.isSuccessful) {
                    val allSessions = response.body()?.sessionServices ?: emptyList()

                    // Busquem si hi ha una sessió activa per aquesta taula
                    val activeSession = allSessions.find { session ->
                        session.idTable == tableId && session.status == SessionStatus.OPEN
                    }

                    if (activeSession != null) {
                        currentSession.value = activeSession
                        // Intentem carregar la comanda associada
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
     * Obre una taula creant una nova sessió al servidor.
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
                    startDate = getCurrentTimestamp(),
                    status = SessionStatus.OPEN
                )
                val response = repository.createSession(token, newSession)

                // Validem si la creació ha estat exitosa
                val body = response.body()
                val isSuccess = response.isSuccessful &&
                        (body?.messageStatus?.equals("success", ignoreCase = true) == true ||
                                !body?.sessionServices.isNullOrEmpty())

                if (isSuccess) {
                    val createdSession = body?.sessionServices?.firstOrNull()
                    if (createdSession != null) {
                        currentSession.value = createdSession
                        // Un cop creada la sessió, inicialitzem la comanda
                        createInitialOrder(token, createdSession.id!!)
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: ""
                    errorMessage.value = "Error creant sessió: ${response.code()} - $errorBody"
                    isLoading.value = false
                }
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
                isLoading.value = false
            }
        }
    }
    // --- Tancar Taula (Cobrar) ---
    fun closeTable(token: String, onSuccess: () -> Unit) {
        val session = currentSession.value ?: return

        viewModelScope.launch {
            isLoading.value = true
            try {
                // Canviem estat a PAID i posem data fi
                val closedSession = session.copy(
                    status = SessionStatus.PAID,
                    endDate = getCurrentTimestamp()
                )
                val response = repository.updateSession(token, closedSession)

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    errorMessage.value = "Error al tancar: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error xarxa: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun addToCart(dish: Dish) {
        cartItems.add(dish)
    }

    /**
     * Envia els plats del carret al servidor.
     * Si la comanda no existeix (cas d'error), intenta recuperar-la abans d'enviar.
     */
    fun sendCart(token: String) {
        if (cartItems.isEmpty()) return

        viewModelScope.launch {
            isLoading.value = true

            // Assegurem que tenim un ID de comanda vàlid
            var orderId = currentOrder.value?.id

            if (orderId == null) {
                val sessionId = currentSession.value?.id
                if (sessionId != null) {
                    val recoveredOrder = fetchOrCreateOrder(token, sessionId)
                    currentOrder.value = recoveredOrder
                    orderId = recoveredOrder?.id
                }
            }

            if (orderId == null) {
                errorMessage.value = "Error fatal: No s'ha pogut recuperar la comanda."
                isLoading.value = false
                return@launch
            }

            var errors = 0
            // Fem una còpia de la llista per iterar sense problemes de concurrència
            val itemsToSend = cartItems.toList()

            itemsToSend.forEach { dish ->
                try {
                    val response = repository.addDishToOrder(token, orderId!!, dish, 1)
                    if (!response.isSuccessful) errors++
                } catch (e: Exception) {
                    errors++
                }
            }

            loadSentItems(token, orderId!!)

            isLoading.value = false

            if (errors == 0) {
                cartItems.clear()
            } else {
                errorMessage.value = "S'han enviat els plats, però $errors han fallat."
                cartItems.clear()
            }
        }
    }

    // --- FUNCIONS PRIVADES ---

    private fun loadOrder(token: String, sessionId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getOrderBySession(token, sessionId)
                val body = response.body()

                if (response.isSuccessful && !body?.items.isNullOrEmpty()) {
                    currentOrder.value = body?.items?.firstOrNull()
                    isLoading.value = false
                } else {
                    // Si no trobem comanda, la creem automàticament
                    createInitialOrder(token, sessionId)
                }
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    private fun loadSentItems(token: String, orderId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getOrderItems(token, orderId)
                if (response.isSuccessful) {
                    val items = response.body()?.items ?: emptyList()
                    sentItems.clear()
                    sentItems.addAll(items)
                }
            } catch (e: Exception) { } finally {
                isLoading.value = false
            }
        }
    }

    private fun createInitialOrder(token: String, sessionId: Long) {
        viewModelScope.launch {
            try {
                val newOrder = Order(
                    idSessionService = sessionId,
                    date = getCurrentTimestamp(),
                    state = OrderStatus.OPEN
                )
                val response = repository.createOrder(token, newOrder)
                val body = response.body()

                val isSuccess = response.isSuccessful &&
                        (body?.messageStatus?.equals("success", ignoreCase = true) == true ||
                                !body?.items.isNullOrEmpty())

                if (isSuccess) {
                    currentOrder.value = body?.items?.firstOrNull()
                } else {
                    errorMessage.value = "Error inicialitzant comanda."
                }
            } catch (e: Exception) {
                errorMessage.value = "Error xarxa ordre: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    /**
     * Intenta obtenir l'ordre existent o la crea si no existeix.
     * Retorna l'objecte Order o null si falla tot el procés.
     */
    private suspend fun fetchOrCreateOrder(token: String, sessionId: Long): Order? {
        // Intent 1: GET
        try {
            val response = repository.getOrderBySession(token, sessionId)
            val body = response.body()
            if (response.isSuccessful && !body?.items.isNullOrEmpty()) {
                return body?.items?.firstOrNull()
            }
        } catch (e: Exception) { e.printStackTrace() }

        // Intent 2: CREATE
        try {
            val newOrder = Order(
                idSessionService = sessionId,
                date = getCurrentTimestamp(),
                state = OrderStatus.OPEN
            )
            val response = repository.createOrder(token, newOrder)
            val body = response.body()
            val isSuccess = response.isSuccessful &&
                    (body?.messageStatus?.equals("success", ignoreCase = true) == true ||
                            !body?.items.isNullOrEmpty())

            if (isSuccess) {
                return body?.items?.firstOrNull()
            }
        } catch (e: Exception) { e.printStackTrace() }

        return null
    }
}