package com.example.mjprestaurant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mjprestaurant.model.dish.Dish
import com.example.mjprestaurant.model.dish.DishCategory
import com.example.mjprestaurant.model.dish.DishRequest
import com.example.mjprestaurant.viewmodel.DishViewModel
import com.example.mjprestaurant.viewmodel.LoginViewModel

/**
 * Pantalla de formulari per crear o editar plats del restaurant.
 *
 * Aquesta pantalla permet als administradors:
 * - Crear nous plats al menú
 * - Editar plats existents
 * - Validar les dades abans de desar
 * - Gestionar la disponibilitat dels plats
 *
 * @param dishId ID del plat a editar (null per crear nou)
 * @param dishViewModel ViewModel que gestiona l'estat dels plats
 * @param loginViewModel ViewModel per obtenir el token d'autenticació
 * @param onSaveSuccess Callback executat quan el plat s'ha desat correctament
 * @param onCancel Callback executat quan es cancel·la l'operació
 *
 * @see Dish
 * @see DishRequest
 * @see DishViewModel
 *
 * @author Martin Muñoz Pozuelo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishFormScreen(
    dishId: Int? = null,
    dishViewModel: DishViewModel,
    loginViewModel: LoginViewModel,
    onSaveSuccess: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    // Estats locals del formulari
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var dishCategory by remember { mutableStateOf(DishCategory.APPETIZER) }
    var available by remember { mutableStateOf(true) }
    var showLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var errors by remember { mutableStateOf(mapOf<String, String>()) }

    val token by loginViewModel.token
    val isLoading by dishViewModel.isLoading

    // Observar cuando termina de cargar para navegar de vuelta
    LaunchedEffect(isLoading) {
        if (!isLoading && showLoading) {
            // Solo navegar si no hay error
            if (dishViewModel.errorMessage.value == null) {
                onSaveSuccess()
            }
            showLoading = false
        }
    }

    // Carregar dades del plat si s'està editant
    LaunchedEffect(dishId) {
        if (dishId != null) {
            // Buscar el plat a editar a la llista del ViewModel
            val dishToEdit = dishViewModel.dishes.value.find { it.id == dishId }
            dishToEdit?.let { dish ->
                name = dish.name
                description = dish.description
                price = String.format("%.2f", dish.price)
                dishCategory = dish.category
                available = dish.available
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (dishId != null) "Editar Plat"
                        else "Nou Plat"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onCancel,
                        enabled = !showLoading && !isLoading
                    ) {
                        Icon(Icons.Default.ArrowBack, "Tornar enrere")
                    }
                },
                actions = {
                    // Botó per desar el plat
                    IconButton(
                        onClick = {
                            if (validateForm(name, description, price)) {
                                showLoading = true
                                errorMessage = null

                                val dishRequest = DishRequest(
                                    name = name.trim(),
                                    description = description.trim(),
                                    price = price.toFloatOrNull() ?: 0.0f,
                                    dishCategory = dishCategory,
                                    available = available
                                )

                                // Utilitzar el ViewModel per crear o actualitzar
                                token?.let { token ->
                                    if (dishId != null) {
                                        dishViewModel.updateDish(token, dishId, dishRequest)
                                    } else {
                                        dishViewModel.createDish(token, dishRequest)
                                    }
                                } ?: run {
                                    errorMessage = "No hi ha token d'autenticació"
                                    showLoading = false
                                }
                            }
                        },
                        enabled = !showLoading && !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(Icons.Default.Check, "Desar plat")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Missatge d'error del ViewModel
                dishViewModel.errorMessage.value?.let { vmError ->
                    if (vmError.isNotBlank()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Text(
                                text = vmError,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                // Missatge d'error local
                if (errorMessage != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = errorMessage ?: "Error desconegut",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                // Formulari
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Camp Nom
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            if (errors.containsKey("name")) {
                                errors = errors - "name"
                            }
                        },
                        label = { Text("Nom del plat *") },
                        isError = errors.containsKey("name"),
                        supportingText = {
                            if (errors.containsKey("name")) {
                                Text(errors["name"] ?: "")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        enabled = !showLoading && !isLoading
                    )

                    // Camp Descripció
                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it
                            if (errors.containsKey("description")) {
                                errors = errors - "description"
                            }
                        },
                        label = { Text("Descripció *") },
                        isError = errors.containsKey("description"),
                        supportingText = {
                            if (errors.containsKey("description")) {
                                Text(errors["description"] ?: "")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        singleLine = false,
                        enabled = !showLoading && !isLoading
                    )

                    // Camp Preu
                    OutlinedTextField(
                        value = price,
                        onValueChange = {
                            if (it.matches(Regex("^\\d*\\.?\\d*$")) && it.length <= 8) {
                                price = it
                                if (errors.containsKey("price")) {
                                    errors = errors - "price"
                                }
                            }
                        },
                        label = { Text("Preu (€) *") },
                        isError = errors.containsKey("price"),
                        supportingText = {
                            if (errors.containsKey("price")) {
                                Text(errors["price"] ?: "")
                            } else {
                                Text("Format: 12.50")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        enabled = !showLoading && !isLoading
                    )

                    // Selector de Categoria
                    Column {
                        Text(
                            "Categoria *",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DishCategory.entries.forEach { category ->
                                FilterChip(
                                    selected = dishCategory == category,
                                    onClick = { dishCategory = category },
                                    label = { Text(category.getDisplayName()) },
                                    enabled = !showLoading && !isLoading
                                )
                            }
                        }
                    }

                    // Switch de Disponibilitat
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Disponible al menú",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Switch(
                            checked = available,
                            onCheckedChange = { available = it },
                            enabled = !showLoading && !isLoading
                        )
                    }

                    // Informació de camps obligatoris
                    Text(
                        "* Camps obligatoris",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Overlay de càrrega
            if (showLoading || isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

/**
 * Valida les dades del formulari abans de desar.
 */
private fun validateForm(name: String, description: String, price: String): Boolean {
    val newErrors = mutableMapOf<String, String>()

    if (name.isBlank()) {
        newErrors["name"] = "El nom és obligatori"
    } else if (name.length < 2) {
        newErrors["name"] = "El nom ha de tenir com a mínim 2 caràcters"
    }

    if (description.isBlank()) {
        newErrors["description"] = "La descripció és obligatòria"
    } else if (description.length < 5) {
        newErrors["description"] = "La descripció ha de tenir com a mínim 5 caràcters"
    }

    val priceNum = price.toFloatOrNull()
    if (price.isBlank()) {
        newErrors["price"] = "El preu és obligatori"
    } else if (priceNum == null) {
        newErrors["price"] = "El preu ha de ser un número vàlid"
    } else if (priceNum <= 0) {
        newErrors["price"] = "El preu ha de ser major a 0"
    } else if (priceNum > 1000) {
        newErrors["price"] = "El preu no pot ser major a 1000€"
    }

    return newErrors.isEmpty()
}