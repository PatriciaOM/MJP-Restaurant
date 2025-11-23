package com.example.mjprestaurant.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mjprestaurant.model.dish.Dish
import com.example.mjprestaurant.model.dish.DishCategory
import com.example.mjprestaurant.viewmodel.DishViewModel
import com.example.mjprestaurant.viewmodel.LoginViewModel

/**
 * Pantalla que mostra el llistat de plats del restaurant amb opcions de filtre i cerca.
 *
 * Aquesta pantalla permet als usuaris:
 * - Veure tots els plats del menú
 * - Filtrar plats per categoria
 * - Cercar plats per nom o descripció
 * - Crear nous plats (només admin)
 * - Editar o eliminar plats existents (només admin)
 *
 * @param dishViewModel ViewModel que gestiona l'estat dels plats
 * @param loginViewModel ViewModel per obtenir el token d'autenticació
 * @param esAdmin Indica si l'usuari actual té permisos d'administrador
 * @param onNewDish Callback executat quan es vol crear un nou plat
 * @param onEditDish Callback executat quan es vol editar un plat
 * @param onGoBack Callback executat quan es vol tornar enrere
 *
 * @see DishViewModel
 * @see Dish
 * @see DishCategory
 *
 * @author Martin Muñoz Pozuelo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishListScreen(
    dishViewModel: DishViewModel,
    loginViewModel: LoginViewModel,
    esAdmin: Boolean = false,
    onNewDish: () -> Unit = {},
    onEditDish: (Long) -> Unit = {},
    onGoBack: () -> Unit = {}
) {
    // Estats observats del ViewModel
    val filteredDishes by dishViewModel.filteredDishes
    val isLoading by dishViewModel.isLoading
    val errorMessage by dishViewModel.errorMessage
    val categoryFilter by dishViewModel.categoryFilter
    val searchText by dishViewModel.searchText
    val token by loginViewModel.token

    // Carregar plats al obrir la pantalla
    LaunchedEffect(Unit) {
        token?.let { dishViewModel.loadDishes(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (esAdmin) "Gestió de Plats"
                        else "Menú del Restaurant"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(Icons.Default.ArrowBack, "Tornar enrere")
                    }
                },
                actions = {
                    // Botó per afegir nou plat (només admin)
                    if (esAdmin) {
                        IconButton(
                            onClick = onNewDish,
                            enabled = !isLoading
                        ) {
                            Icon(Icons.Default.Add, "Afegir nou plat")
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
        ) {
            // Secció de filtres i cerca
            FiltersSection(
                selectedCategory = categoryFilter,
                searchText = searchText,
                onCategoryChanged = dishViewModel::changeCategoryFilter,
                onSearchTextChanged = dishViewModel::changeSearchText,
                modifier = Modifier.padding(16.dp)
            )

            // Contingut principal
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                errorMessage != null -> {
                    ErrorSection(
                        message = errorMessage ?: "Error desconegut",
                        onRetry = { token?.let { dishViewModel.loadDishes(it) } },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                filteredDishes.isEmpty() -> {
                    EmptyListSection(
                        searchText = searchText,
                        categoryFilter = categoryFilter,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    DishesListSection(
                        dishes = filteredDishes,
                        isAdmin = esAdmin,
                        onEditDish = onEditDish,
                        onDeleteDish = { dishId ->
                            token?.let { dishViewModel.deleteDish(it, dishId) }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

/**
 * Secció que mostra els controls de filtre i cerca.
 */
@Composable
private fun FiltersSection(
    selectedCategory: DishCategory?,
    searchText: String,
    onCategoryChanged: (DishCategory?) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Barra de cerca
        OutlinedTextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            label = { Text("Cercar plats...") },
            leadingIcon = { Icon(Icons.Default.Search, "Cercar") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Filtres per categoria
        Text(
            "Filtrar per categoria:",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Botó "Totes"
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategoryChanged(null) },
                label = { Text("Totes") }
            )

            // Botons per cada categoria
            DishCategory.entries.forEach { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = {
                        onCategoryChanged(
                            if (selectedCategory == category) null
                            else category
                        )
                    },
                    label = { Text(category.getDisplayName()) }
                )
            }
        }
    }
}

/**
 * Secció que mostra la llista de plats.
 */
@Composable
private fun DishesListSection(
    dishes: List<Dish>,
    isAdmin: Boolean,
    onEditDish: (Long) -> Unit,
    onDeleteDish: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var dishToDelete by remember { mutableStateOf<Dish?>(null) }

    // Diàleg de confirmació per eliminar plat
    if (dishToDelete != null) {
        AlertDialog(
            onDismissRequest = { dishToDelete = null },
            title = { Text("Eliminar plat") },
            text = {
                Text("Estàs segur que vols eliminar \"${dishToDelete?.name}\"?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        dishToDelete?.id?.let { onDeleteDish(it) }  // it és Long
                        dishToDelete = null
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { dishToDelete = null }) {
                    Text("Cancel·lar")
                }
            }
        )
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(dishes) { dish ->

            dish.id?.let { dishId ->
                DishItem(
                    dish = dish,
                    isAdmin = isAdmin,
                    onEdit = { onEditDish(dishId) },
                    onDelete = { dishToDelete = dish }
                )
            }
        }
    }
}

/**
 * Targeta que mostra la informació d'un plat individual.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DishItem(
    dish: Dish,
    isAdmin: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (!dish.available) {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        onClick = { if (isAdmin) onEdit() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Capçalera amb nom, preu i estat
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        dish.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        dish.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "%.2f €".format(dish.price),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        dish.category.getDisplayName(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Peu amb disponibilitat i accions (només admin)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                // Estat de disponibilitat
                Text(
                    if (dish.available) "✅ Disponible" else "❌ No disponible",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (dish.available) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.error
                )

                // Accions d'administrador
                if (isAdmin) {
                    Row {
                        TextButton(onClick = onEdit) {
                            Text("Editar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(
                            onClick = onDelete,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}

/**
 * Secció que es mostra quan hi ha un error.
 */
@Composable
private fun ErrorSection(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Error carregant plats",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Tornar a intentar")
        }
    }
}

/**
 * Secció que es mostra quan la llista de plats està buida.
 */
@Composable
private fun EmptyListSection(
    searchText: String,
    categoryFilter: DishCategory?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "No s'han trobat plats",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            when {
                searchText.isNotBlank() ->
                    "Prova amb una altra cerca o treu els filtres"
                categoryFilter != null ->
                    "No hi ha plats a la categoria ${categoryFilter.getDisplayName()}"
                else -> "Encara no hi ha plats al menú"
            },
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

/**
 * Previsualització d'un element de plat.
 */
@Preview(showBackground = true)
@Composable
fun DishItemPreview() {
    DishItem(
        dish = Dish(
            id = 1,
            name = "Paella",
            description = "Arròs amb marisc fresc",
            price = 15.50f,
            category = DishCategory.MAIN,
            available = true
        ),
        isAdmin = true,
        onEdit = {},
        onDelete = {}
    )
}