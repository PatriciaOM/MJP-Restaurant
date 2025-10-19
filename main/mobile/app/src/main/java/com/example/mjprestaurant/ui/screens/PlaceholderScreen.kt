package com.example.mjprestaurant.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mjprestaurant.viewmodel.LoginViewModel

/**
 * Pantalla temporal principal que es mostra tras login exitos.
 *
 * Aquesta pantalla actúa com un placeholder per a la funcionalitat futura de gestió de comandes.
 *
 * Proporciona:
 *  - Missatge indicatiu de la funcionalitat plantejada.
 *  - Butó er tancar sessió de l'usuari.
 *  - Integració amb el ViewModel per gestionar l'estat.
 *
 *  @param viewModel viewModel que conté l'estat d'autenticació i la lògica.
 *  @param onLogout Callback executat quan l'usuari sol·licita tancar sessió, utilitzat per navegar
 *  a pantalla de Login
 *
 *  @see LoginScreen
 *  @see LoginViewModel
 *  @see MainActivity
 *
 *  @author Martin Muñoz Pozuelo
 */
@Composable
fun PlaceholderScreen(
    viewModel: LoginViewModel,
    onLogout: () -> Unit
) {
    /**
     * Layout principal de la pantalla placeholder.
     *
     * Utilitza Column centrada que conté:
     *  - Text informatiu sobre la funcionalitat futura.
     *  - Espaciador per separació visual
     *  - Butó de logout que executa la logica al tancament de sessió.
     */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Text informatiu sobre la funcionalitat futura.
        Text("Pantalla de comandes (placeholder)")
        Spacer(modifier = Modifier.height(20.dp))
        /**
         * Butó per tancar sessió.
         *
         * Al fer click:
         *  - Executa onLogoutClick() del ViewModel per netejar estat.
         *  - Truca al callback onLogout per navegar a la pantalla de login.
         */
        Button(onClick = {
            // Executa la lògica del Logout en el viewModel
            viewModel.onLogoutClick()
            // navega a la pantalla de login
            onLogout()
        }) {
            Text("Logout")
        }
    }
}

