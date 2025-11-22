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
import androidx.compose.ui.graphics.Color
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
    dishId: Long? = null,
    dishViewModel: DishViewModel,
    loginViewModel: LoginViewModel,
    onSaveSuccess: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    // Estats del formulari
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var dishCategory by remember { mutableStateOf(DishCategory.APPETIZER) }
    var available by remember { mutableStateOf(true) }
    var formErrors by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    var hasSubmitted by remember { mutableStateOf(false) }

    // Estats del ViewModel
    val token = loginViewModel.token.value
    val isLoading = dishViewModel.isLoading.value
    val errorMessage = dishViewModel.errorMessage.value

    // Carregar dades si s'edita
    LaunchedEffect(dishId) {
        if (dishId != null) {
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

    // Observar finalització de càrrega
    LaunchedEffect(isLoading) {
        if (hasSubmitted && !isLoading && errorMessage == null) {
            onSaveSuccess()
        }
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            hasSubmitted = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (dishId != null) "Editar Plat" else "Nou Plat")
                },
                navigationIcon = {
                    IconButton(onClick = onCancel, enabled = !isLoading) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tornar")
                    }
                },
                actions = {
                    if (isLoading) {
                        CircularProgressIndicator(Modifier.size(20.dp))
                    } else {
                        IconButton(
                            onClick = {
                                val errors = validateForm(name, description, price)
                                if (errors.isEmpty()) {
                                    dishViewModel.clearError()

                                    val dishRequest = DishRequest(
                                        name = name.trim(),
                                        description = description.trim(),
                                        price = price.toFloat(),
                                        dishCategory = dishCategory,
                                        available = available
                                    )

                                    if (token != null) {
                                        if (dishId != null) {
                                            dishViewModel.updateDish(token, dishId, dishRequest)
                                        } else {
                                            dishViewModel.createDish(token, dishRequest)
                                        }
                                    }
                                } else {
                                    formErrors = errors
                                }
                            },
                            enabled = !isLoading
                        ) {
                            Icon(Icons.Default.Check, contentDescription = "Desar")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Error del ViewModel
            if (errorMessage != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(
                        errorMessage,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            // Formulari
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Nom
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom del plat *") },
                    isError = formErrors.containsKey("name"),
                    supportingText = {
                        formErrors["name"]?.let { error ->
                            Text(error)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                )

                // Descripció
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripció *") },
                    isError = formErrors.containsKey("description"),
                    supportingText = {
                        formErrors["description"]?.let { error ->
                            Text(error)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    enabled = !isLoading
                )

                // Preu
                OutlinedTextField(
                    value = price,
                    onValueChange = {
                        if (it.matches(Regex("^\\d*\\.?\\d*$"))) {
                            price = it
                        }
                    },
                    label = { Text("Preu (€) *") },
                    isError = formErrors.containsKey("price"),
                    supportingText = {
                        formErrors["price"]?.let { error ->
                            Text(error)
                        } ?: Text("Format: 12.50")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    enabled = !isLoading
                )

                // Categoria
                Text("Categoria *", style = MaterialTheme.typography.labelMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DishCategory.entries.forEach { category ->
                        FilterChip(
                            selected = dishCategory == category,
                            onClick = { dishCategory = category },
                            label = { Text(category.getDisplayName()) },
                            enabled = !isLoading
                        )
                    }
                }

                // Disponible
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Disponible al menú")
                    Switch(
                        checked = available,
                        onCheckedChange = { available = it },
                        enabled = !isLoading
                    )
                }

                Text("* Camps obligatoris", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

private fun validateForm(name: String, description: String, price: String): Map<String, String> {
    val errors = mutableMapOf<String, String>()

    if (name.isBlank()) {
        errors["name"] = "El nom és obligatori"
    } else if (name.length < 2) {
        errors["name"] = "Mínim 2 caràcters"
    }

    if (description.isBlank()) {
        errors["description"] = "La descripció és obligatòria"
    } else if (description.length < 5) {
        errors["description"] = "Mínim 5 caràcters"
    }

    val priceNum = price.toFloatOrNull()
    if (price.isBlank()) {
        errors["price"] = "El preu és obligatori"
    } else if (priceNum == null) {
        errors["price"] = "Preu ha de ser un número"
    } else if (priceNum <= 0) {
        errors["price"] = "Preu ha de ser major a 0"
    }

    return errors
}