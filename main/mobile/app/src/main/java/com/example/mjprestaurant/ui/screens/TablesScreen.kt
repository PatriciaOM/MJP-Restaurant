package com.example.mjprestaurant.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mjprestaurant.model.TableStatus
import com.example.mjprestaurant.viewmodel.LoginViewModel
import com.example.mjprestaurant.viewmodel.TablesViewModel

/**
 * Pantalla que mostra la llista de taules del restaurant amb el seu estat actual.
 *
 * Aquesta pantalla permet als cambrers:
 * - Veure totes les taules i el seu estat (lliure/ocupada)
 * - Veure la capacitat i comensals actuals de cada taula
 * - Seleccionar taules lliures per a obrir-ne sessió
 * - Actualitzar manualment l'estat de les taules
 *
 * @param tablesViewModel ViewModel que gestiona l'estat de les taules
 * @param loginViewModel ViewModel d'autenticació per a obtenir el token
 * @param onLogout Callback executat quan l'usuari tanca sessió
 *
 * @see TablesViewModel
 * @see LoginViewModel
 *
 * @author Martin Muñoz Pozuelo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TablesScreen(
    tablesViewModel: TablesViewModel = viewModel(),
    loginViewModel: LoginViewModel,
    onLogout: () -> Unit = {}
) {
    val taules by tablesViewModel.taules
    val isLoading by tablesViewModel.isLoading
    val errorMessage by tablesViewModel.errorMessage
    val token by loginViewModel.token

    // Carregar taules al obrir la pantalla
    LaunchedEffect(Unit) {
        token?.let { tablesViewModel.loadTables(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Taules") },
                actions = {
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
                            TaulaItemSimple(taula = taula)
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
 *
 * @see TableStatus
 */
@Composable
fun TaulaItemSimple(taula: TableStatus) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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

/**
 * Previsualització de la pantalla de taules en Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun TablesScreenPreview() {
    // Per a la previsualització, crear un LoginViewModel amb dades de prova
    val fakeLoginViewModel = LoginViewModel().apply {
        token.value = "fake_token_for_preview"
    }

    TablesScreen(
        loginViewModel = fakeLoginViewModel,
        onLogout = {}
    )
}

/**
 * Previsualització d'una targeta de taula.
 */
@Preview(showBackground = true)
@Composable
fun TaulaItemSimplePreview() {
    TaulaItemSimple(
        taula = TableStatus(id = 1, maxClients = 6, clientsAmount = 4)
    )
}