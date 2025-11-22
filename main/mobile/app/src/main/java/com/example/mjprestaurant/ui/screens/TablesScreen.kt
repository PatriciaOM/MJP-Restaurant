package com.example.mjprestaurant.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mjprestaurant.model.table.TableStatus
import com.example.mjprestaurant.viewmodel.LoginViewModel
import com.example.mjprestaurant.viewmodel.TablesViewModel

/**
 * Pantalla que mostra la llista de taules del restaurant amb el seu estat actual.
 *
 * Aquesta pantalla permet als cambrers:
 * - Veure totes les taules i el seu estat (lliure/ocupada)
 * - Veure la capacitat i comensals actuals de cada taula
 * - Seleccionar una taula per veure'n el detall i gestionar la sessió
 * - Actualitzar manualment l'estat de les taules
 * - Accedir a la gestió de plats (només administradors)
 *
 * @param tablesViewModel ViewModel que gestiona l'estat de les taules
 * @param loginViewModel ViewModel d'autenticació per a obtenir el token i rol
 * @param onLogout Callback executat quan l'usuari tanca sessió
 * @param onGestioPlats Callback executat quan es vol accedir a la gestió de plats
 * @param onTableClick Callback executat quan es selecciona una taula específica (passant el seu ID)
 *
 * @see TablesViewModel
 * @see LoginViewModel
 * @see TableStatus
 *
 * @author Martin Muñoz Pozuelo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TablesScreen(
    tablesViewModel: TablesViewModel,
    loginViewModel: LoginViewModel,
    onLogout: () -> Unit = {},
    onGestioPlats: () -> Unit = {},
    onTableClick: (Long) -> Unit = {}
) {
    val taules by tablesViewModel.taules
    val isLoading by tablesViewModel.isLoading
    val errorMessage by tablesViewModel.errorMessage
    val token by loginViewModel.token
    val esAdmin by remember { derivedStateOf { loginViewModel.role.value == "admin" } }

    // Carregar taules al obrir la pantalla
    LaunchedEffect(Unit) {
        token?.let { tablesViewModel.loadTables(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Taules") },
                actions = {
                    // Botó per a gestió de plats (només administradors)
                    if (esAdmin) {
                        IconButton(
                            onClick = onGestioPlats,
                            enabled = !isLoading
                        ) {
                            Icon(Icons.Default.Settings, "Gestió de Plats")
                        }
                    }

                    // Botó per a actualitzar les taules
                    IconButton(
                        onClick = { token?.let { tablesViewModel.refreshTables(it) } },
                        enabled = !isLoading
                    ) {
                        Icon(Icons.Default.Refresh, "Actualitzar")
                    }

                    // Botó per a tancar sessió
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, "Tancar sessió")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            when {
                // Estat de càrrega
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                // Error en carregar les taules
                errorMessage != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Error: ${errorMessage ?: "Desconegut"}")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { token?.let { tablesViewModel.refreshTables(it) } }) {
                            Text("Tornar a intentar")
                        }
                    }
                }

                // Llista de taules carregada correctament
                else -> {
                    LazyColumn {
                        items(taules) { taula ->
                            TaulaItemSimple(
                                taula = taula,
                                onClick = { onTableClick(taula.id) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

/**
 * Targeta que mostra la informació d'una taula individual.
 *
 * @param taula Estat de la taula a mostrar
 * @param onClick Callback executat al prèmer la taula
 *
 * @see TableStatus
 */
@Composable
fun TaulaItemSimple(
    taula: TableStatus,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick), // Fem la targeta clicable
        colors = CardDefaults.cardColors(
            containerColor = if (taula.estaOcupada()) {
                MaterialTheme.colorScheme.errorContainer
            } else {
                MaterialTheme.colorScheme.primaryContainer
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "Taula ${taula.id}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text("Capacitat: ${taula.getCapacityText()}")
            }
            Text(
                taula.getStatusText(),
                style = MaterialTheme.typography.bodyMedium,
                color = if (taula.estaOcupada()) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.primary
            )
        }
    }
}