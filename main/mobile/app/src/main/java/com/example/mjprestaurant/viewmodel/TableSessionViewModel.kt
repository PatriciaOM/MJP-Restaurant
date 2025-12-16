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
     * Es considera ocupada qualsevol taula que no tingui l'estat CLOSED, permetent recuperar
     * estats intermedis o bloquejos del servidor.
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

                    // Busquem qualsevol sessió activa (que no estigui tancada definitivament)
                    val activeSession = allSessions.find { session ->
                        session.idTable == tableId && session.status != SessionStatus.CLOSED
                    }

                    if (activeSession != null) {
                        currentSession.value = activeSession
                        // Carreguem la comanda associada a aquesta sessió
                        loadOrder(token, activeSession.id!!)
                    } else {
                        isLoading.value = false // Taula lliure
                        clearLocalState()
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
     * Inclou lògica de recuperació automàtica en cas que la taula ja estigui marcada com a ocupada (423).
     */
    fun openTable(token: String, tableId: Long, diners: Int) {
        viewModelScope.launch {
            isLoading.value = true
            clearLocalState()

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

                // Si el servidor retorna Locked (423), significa que ja existeix una sessió activa.
                // Iniciem el procés de recuperació per gestionar l'estat existent.
                if (response.code() == 423) {
                    recoverBlockedSession(token, tableId)
                    return@launch
                }

                // Validem si la creació ha estat exitosa
                val body = response.body()
                val isSuccess = response.isSuccessful &&
                        (body?.messageStatus?.equals("success", ignoreCase = true) == true ||
                                !body?.sessionServices.isNullOrEmpty())

                if (isSuccess) {
                    val createdSession = body?.sessionServices?.firstOrNull()
                    if (createdSession != null) {
                        currentSession.value = createdSession
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

    /**
     * Recupera i gestiona una sessió quan no es pot crear una de nova.
     * Si la sessió existent està tancada (estat inconsistent), la reobre per permetre operacions
     * i reinicia la comanda per al nou servei.
     */
    private fun recoverBlockedSession(token: String, tableId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getSessions(token)
                val allSessions = response.body()?.sessionServices ?: emptyList()

                // Intentem trobar una sessió activa explícita
                var blockedSession = allSessions.find { session ->
                    session.idTable == tableId && session.status != SessionStatus.CLOSED
                }

                // Si no en trobem cap d'activa però la taula està bloquejada, recuperem l'última sessió registrada
                if (blockedSession == null) {
                    blockedSession = allSessions
                        .filter { it.idTable == tableId }
                        .maxByOrNull { it.id ?: 0 }
                }

                if (blockedSession != null) {
                    var isRevived = false

                    // Si la sessió recuperada està tancada, la reobrim (OPEN) per permetre crear comandes
                    if (blockedSession!!.status == SessionStatus.CLOSED) {
                        val revivedSession = blockedSession!!.copy(
                            status = SessionStatus.OPEN,
                            endDate = null // Eliminem la data de fi per reactivar-la
                        )
                        val updateResponse = repository.updateSession(token, revivedSession)

                        if (updateResponse.isSuccessful) {
                            blockedSession = updateResponse.body()?.sessionServices?.firstOrNull() ?: revivedSession
                            isRevived = true
                        }
                    }

                    currentSession.value = blockedSession

                    // Si hem reactivat una sessió antiga, la tractem com un servei nou i creem una comanda neta.
                    // Si no, simplement carreguem l'estat actual.
                    if (isRevived) {
                        createInitialOrder(token, blockedSession!!.id!!)
                    } else {
                        loadOrder(token, blockedSession!!.id!!)
                    }

                } else {
                    errorMessage.value = "Error: Taula bloquejada però sense cap sessió recuperable."
                    isLoading.value = false
                }
            } catch (e: Exception) {
                errorMessage.value = "Error recuperant sessió: ${e.message}"
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
                // Canviem estat a CLOSED i posem data fi per finalitzar el servei
                val closedSession = session.copy(
                    status = SessionStatus.CLOSED,
                    endDate = getCurrentTimestamp()
                )
                val response = repository.updateSession(token, closedSession)

                if (response.isSuccessful) {
                    clearLocalState()
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
     * Si la comanda no existeix, intenta crear-la o recuperar-la abans d'enviar els items.
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
                errorMessage.value = "Error fatal: No s'ha pogut obtenir la comanda."
                isLoading.value = false
                return@launch
            }

            var errors = 0
            val itemsToSend = cartItems.toList()

            itemsToSend.forEach { dish ->
                try {
                    val response = repository.addDishToOrder(token, orderId!!, dish, 1)
                    if (!response.isSuccessful) errors++
                } catch (e: Exception) {
                    errors++
                }
            }

            if (errors == 0) {
                try {
                    val updatedOrder = currentOrder.value!!.copy(state = OrderStatus.SENDED)
                    repository.updateOrder(token, updatedOrder)
                    currentOrder.value = updatedOrder
                } catch (e: Exception) {
                    e.printStackTrace()
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

    private fun clearLocalState() {
        currentSession.value = null
        currentOrder.value = null
        cartItems.clear()
        sentItems.clear()
    }

    private fun loadOrder(token: String, sessionId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getOrderBySession(token, sessionId)
                val body = response.body()

                if (response.isSuccessful && !body?.items.isNullOrEmpty()) {
                    // Filtrem per obtenir la comanda activa més recent (OPEN o SENDED),
                    // evitant carregar comandes antigues si la sessió s'ha reutilitzat.
                    val activeOrder = body?.items
                        ?.filter { it.state == OrderStatus.OPEN || it.state == OrderStatus.SENDED }
                        ?.maxByOrNull { it.id ?: 0 }

                    if (activeOrder != null) {
                        currentOrder.value = activeOrder
                        loadSentItems(token, activeOrder.id!!)
                    } else {
                        // Si només hi ha històric, creem una nova comanda per al servei actual
                        createInitialOrder(token, sessionId)
                    }
                } else {
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
                    // Seleccionem la nova ordre creada assegurant que és l'última (maxBy ID)
                    val createdOrder = body?.items
                        ?.filter { it.state == OrderStatus.OPEN }
                        ?.maxByOrNull { it.id ?: 0 }
                        ?: body?.items?.firstOrNull()

                    currentOrder.value = createdOrder
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

    private suspend fun fetchOrCreateOrder(token: String, sessionId: Long): Order? {
        try {
            val response = repository.getOrderBySession(token, sessionId)
            val body = response.body()
            if (response.isSuccessful && !body?.items.isNullOrEmpty()) {
                // Prioritzem sempre l'ordre activa més recent
                return body?.items
                    ?.filter { it.state == OrderStatus.OPEN || it.state == OrderStatus.SENDED }
                    ?.maxByOrNull { it.id ?: 0 }
                    ?: body?.items?.firstOrNull()
            }
        } catch (e: Exception) { e.printStackTrace() }

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
                return body?.items
                    ?.filter { it.state == OrderStatus.OPEN }
                    ?.maxByOrNull { it.id ?: 0 }
                    ?: body?.items?.firstOrNull()
            }
        } catch (e: Exception) { e.printStackTrace() }

        return null
    }
}