package com.example.mjprestaurant.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mjprestaurant.viewmodel.LoginViewModel

@Composable
fun PlaceholderScreen(
    viewModel: LoginViewModel,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Pantalla de comandes (placeholder)")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            viewModel.onLogoutClick()
            onLogout()
        }) {
            Text("Logout")
        }
    }
}