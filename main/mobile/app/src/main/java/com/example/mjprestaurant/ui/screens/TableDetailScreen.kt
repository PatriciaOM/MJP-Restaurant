package com.example.mjprestaurant.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mjprestaurant.viewmodel.LoginViewModel
import com.example.mjprestaurant.viewmodel.TableSessionViewModel
import com.example.mjprestaurant.viewmodel.TablesViewModel

/**
 * Pantalla per a gestionar l'obertura d'una taula (Crear Sessió).
 * Corregit per evitar que es quedi penjat a "Obrint comanda...".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableDetailScreen(
    tableId: Long,
    tableSessionViewModel: TableSessionViewModel,
    tablesViewModel: TablesViewModel,
    loginViewModel: LoginViewModel,
    onBack: () -> Unit,
    onTableOpened: () -> Unit
) {
    val token by loginViewModel.token
    val currentSession by tableSessionViewModel.currentSession
    // També observem l'ordre per assegurar que tot està llest
    val currentOrder by tableSessionViewModel.currentOrder
    val isLoading by tableSessionViewModel.isLoading
    val errorMessage by tableSessionViewModel.errorMessage

    // Obtenir capacitat màxima de la taula actual
    val tableInfo = tablesViewModel.taules.value.find { it.id == tableId }
    val maxCapacity = tableInfo?.maxClients ?: 4

    // Carregar dades al entrar
    LaunchedEffect(tableId) {
        token?.let { tableSessionViewModel.loadTableSession(it, tableId) }
    }

    // OBSERVADOR D'ESTAT CORREGIT:
    // Ara vigilem currentSession, currentOrder i isLoading.
    // Així quan isLoading passa a false, això es torna a executar i navega.
    LaunchedEffect(currentSession, currentOrder, isLoading) {
        // Si tenim sessió, tenim ordre (o l'intent ha acabat) i ja no carreguem...
        if (currentSession != null && currentOrder != null && !isLoading) {
            onTableOpened()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Obrir Taula $tableId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Tornar")
                    }
                }
            )
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

                    // Si no tenim sessió creada, mostrem el formulari
                    if (currentSession == null) {
                        EmptyTableContent(
                            maxCapacity = maxCapacity,
                            onOpenTable = { diners ->
                                token?.let { tableSessionViewModel.openTable(it, tableId, diners) }
                            }
                        )
                    } else {
                        // Si tenim sessió però estem esperant l'ordre o la navegació
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Sessió creada. Inicialitzant comanda...", style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyTableContent(
    maxCapacity: Int,
    onOpenTable: (Int) -> Unit
) {
    var diners by remember { mutableStateOf(2) }
    val isOverCapacity = diners > maxCapacity

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
            "Nova Sessió",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Capacitat màxima: $maxCapacity",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text("Nombre de comensals: $diners", style = MaterialTheme.typography.titleLarge)

        if (isOverCapacity) {
            Text("⚠️ Atenció: Supera la capacitat", color = MaterialTheme.colorScheme.error)
        }

        Slider(
            value = diners.toFloat(),
            onValueChange = { diners = it.toInt() },
            valueRange = 1f..12f,
            steps = 10,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onOpenTable(diners) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isOverCapacity) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        ) {
            Text(if (isOverCapacity) "Obrir (Sobrecàrrega)" else "Obrir Taula", style = MaterialTheme.typography.titleMedium)
        }
    }
}