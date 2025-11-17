package com.example.mjprestaurant.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mjprestaurant.model.order.Order
import com.example.mjprestaurant.model.session.SessionService
import com.example.mjprestaurant.viewmodel.LoginViewModel
import com.example.mjprestaurant.viewmodel.TableSessionViewModel

/**
 * Pantalla de detall d'una taula.
 *
 * Gestiona dos estats principals:
 * 1. Taula Lliure: Permet seleccionar comensals i obrir sessió.
 * 2. Taula Ocupada: Mostra la comanda actual i permet afegir plats.
 *
 * @param tableId ID de la taula seleccionada.
 * @param tableSessionViewModel ViewModel per a la lògica de sessió/comanda.
 * @param loginViewModel ViewModel per al token d'usuari.
 * @param onBack Callback per tornar enrere.
 * @param onAddDish Callback per anar a la selecció de plats.
 *
 * @author Martin Muñoz Pozuelo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableDetailScreen(
    tableId: Long,
    tableSessionViewModel: TableSessionViewModel,
    loginViewModel: LoginViewModel,
    onBack: () -> Unit,
    onAddDish: () -> Unit = {}
) {
    val token by loginViewModel.token
    val currentSession by tableSessionViewModel.currentSession
    val currentOrder by tableSessionViewModel.currentOrder
    val isLoading by tableSessionViewModel.isLoading
    val errorMessage by tableSessionViewModel.errorMessage

    // Carregar dades al entrar
    LaunchedEffect(tableId) {
        token?.let { tableSessionViewModel.loadTableSession(it, tableId) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Taula $tableId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Tornar")
                    }
                }
            )
        },
        floatingActionButton = {
            // Només mostrem el botó d'afegir si la taula està oberta
            if (currentSession != null && !isLoading) {
                FloatingActionButton(onClick = onAddDish) {
                    Icon(Icons.Default.Add, "Afegir Plat")
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else {
                Column(modifier = Modifier.fillMaxSize()) {

                    // Mostrar errors si n'hi ha
                    if (errorMessage != null) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                        ) {
                            Text(
                                text = errorMessage ?: "",
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    // Lògica principal: Lliure vs Ocupada
                    if (currentSession == null) {
                        // VISTA TAULA LLIURE
                        EmptyTableContent(
                            onOpenTable = { diners ->
                                token?.let { tableSessionViewModel.openTable(it, tableId, diners) }
                            }
                        )
                    } else {
                        // VISTA TAULA OCUPADA
                        ActiveSessionContent(
                            session = currentSession!!,
                            order = currentOrder
                        )
                    }
                }
            }
        }
    }
}

/**
 * Contingut quan la taula està lliure.
 * Mostra selector de comensals i botó per obrir.
 */
@Composable
private fun EmptyTableContent(onOpenTable: (Int) -> Unit) {
    var diners by remember { mutableStateOf(2) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Taula Lliure",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text("Nombre de comensals: $diners", style = MaterialTheme.typography.titleLarge)

        Slider(
            value = diners.toFloat(),
            onValueChange = { diners = it.toInt() },
            valueRange = 1f..10f,
            steps = 9,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onOpenTable(diners) },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Obrir Taula")
        }
    }
}

/**
 * Contingut quan la taula està ocupada.
 * Mostra info de la sessió i la comanda.
 */
@Composable
private fun ActiveSessionContent(
    session: SessionService,
    order: Order?
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Info Sessió
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Sessió Activa", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Comensals: ${session.clients}")
                Text("Inici: ${session.startDate ?: "Desconegut"}")
                Text("Cambrer ID: ${session.waiterId}")
            }
        }

        // Info Comanda
        Text("Comanda", style = MaterialTheme.typography.titleLarge)

        if (order != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Ordre #${order.id}", fontWeight = FontWeight.Bold)
                        Text(order.state.toString(), color = MaterialTheme.colorScheme.primary)
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    // TODO: Aquí anirà la llista de plats quan el backend suporti OrderItems
                    Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                        Text(
                            "Llista de plats buida (Pendent d'implementació al backend)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
        } else {
            Text("Creant comanda...", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }
}