package com.example.mjprestaurant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.mjprestaurant.model.dish.Dish
import com.example.mjprestaurant.model.dish.DishCategory
import com.example.mjprestaurant.model.order.OrderItem
import com.example.mjprestaurant.viewmodel.DishViewModel
import com.example.mjprestaurant.viewmodel.LoginViewModel
import com.example.mjprestaurant.viewmodel.TableSessionViewModel

/**
 * Pantalla principal de comanda (TPV).
 *
 * Permet:
 * - Afegir plats al carret local.
 * - Enviar el carret al servidor (Confirmar Comanda).
 * - Veure l'històric de plats enviats.
 * - Tancar la taula i cobrar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    tableId: Long,
    tableSessionViewModel: TableSessionViewModel,
    dishViewModel: DishViewModel,
    loginViewModel: LoginViewModel,
    onBack: () -> Unit
) {
    val token by loginViewModel.token
    val dishes by dishViewModel.filteredDishes
    val selectedCategory by dishViewModel.categoryFilter

    val cartItems = tableSessionViewModel.cartItems
    val sentItems = tableSessionViewModel.sentItems // PLATS JA ENVIATS
    val isLoading by tableSessionViewModel.isLoading
    val errorMessage by tableSessionViewModel.errorMessage

    // Estats per als diàlegs
    var showSentItemsDialog by remember { mutableStateOf(false) }
    var showCloseTableDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        token?.let {
            tableSessionViewModel.loadTableSession(it, tableId)
            dishViewModel.loadDishes(it)
        }
    }

    // --- DIÀLEG DE CONFIRMACIÓ: COBRAR I TANCAR ---
    if (showCloseTableDialog) {
        AlertDialog(
            onDismissRequest = { showCloseTableDialog = false },
            title = { Text("Tancar Taula i Cobrar") },
            text = { Text("Segur que vols tancar la sessió? La taula passarà a PAGADA i quedarà lliure.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showCloseTableDialog = false
                        token?.let {
                            // Cridem a la funció del ViewModel per tancar
                            tableSessionViewModel.closeTable(it, onSuccess = {
                                // Si tot va bé, tornem a la llista de taules
                                onBack()
                            })
                        }
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCloseTableDialog = false }) {
                    Text("Cancel·lar")
                }
            }
        )
    }

    // --- DIÀLEG PER VEURE PLATS ENVIATS A CUINA ---
    if (showSentItemsDialog) {
        Dialog(onDismissRequest = { showSentItemsDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 300.dp, max = 600.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Plats a Cuina", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(16.dp))

                    if (sentItems.isEmpty()) {
                        Text("Encara no s'ha enviat res a cuina.", color = Color.Gray)
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(sentItems) { item ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("${item.amount}x ${item.name}", fontWeight = FontWeight.Bold)
                                    Text("${item.price} €")
                                }
                                Divider(color = Color.LightGray, thickness = 0.5.dp)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { showSentItemsDialog = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Tancar")
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Mesa $tableId")
                        if (cartItems.isNotEmpty()) {
                            Text(
                                "${cartItems.size} plats pendents",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Tornar")
                    }
                },
                actions = {
                    // BOTÓ PER VEURE EL QUE JA ESTÀ A CUINA
                    IconButton(onClick = { showSentItemsDialog = true }) {
                        BadgedBox(badge = {
                            if (sentItems.isNotEmpty()) Badge { Text("${sentItems.size}") }
                        }) {
                            Icon(Icons.Default.List, "Veure enviats")
                        }
                    }
                    // BOTÓ PER COBRAR I TANCAR LA TAULA (NOU)
                    IconButton(onClick = { showCloseTableDialog = true }) {
                        Icon(Icons.Default.Check, "Cobrar", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        },
        floatingActionButton = {
            if (cartItems.isNotEmpty() && !isLoading) {
                ExtendedFloatingActionButton(
                    onClick = { token?.let { tableSessionViewModel.sendCart(it) } },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Default.ShoppingCart, "Enviar")
                    Spacer(Modifier.width(8.dp))
                    Text("ENVIAR (${cartItems.size})")
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (errorMessage != null) {
                    Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
                }

                val selectedTabIndex = if (selectedCategory == null) 0 else DishCategory.entries.indexOf(selectedCategory) + 1

                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    edgePadding = 16.dp,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    indicator = { tabPositions ->
                        if (selectedTabIndex < tabPositions.size) {
                            TabRowDefaults.Indicator(modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]))
                        }
                    }
                ) {
                    Tab(selected = selectedCategory == null, onClick = { dishViewModel.changeCategoryFilter(null) }, text = { Text("Tots") })
                    DishCategory.entries.forEach { category ->
                        Tab(selected = selectedCategory == category, onClick = { dishViewModel.changeCategoryFilter(category) }, text = { Text(category.getDisplayName()) })
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(dishes) { dish ->
                        DishCard(
                            dish = dish,
                            onAdd = { tableSessionViewModel.addToCart(dish) }
                        )
                    }
                }
            }
            if (isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun DishCard(dish: Dish, onAdd: () -> Unit) {
    Card(
        onClick = onAdd,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box(modifier = Modifier.height(120.dp).fillMaxWidth()) {
                AsyncImage(
                    model = dish.getValidImageUrl(),
                    contentDescription = dish.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = dish.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = dish.description, style = MaterialTheme.typography.bodySmall, color = Color.Gray, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.height(40.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = dish.getPriceFormatted(), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    FilledIconButton(onClick = onAdd, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Add, "Afegir", modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}