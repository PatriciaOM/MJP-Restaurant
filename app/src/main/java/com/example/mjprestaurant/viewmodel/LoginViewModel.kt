package com.example.mjprestaurant.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.mjprestaurant.network.AuthRepository


class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    // Campos de texto
    var username = mutableStateOf("")
    var password = mutableStateOf("")

    // Estados UI
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)
    var isLoggedIn = mutableStateOf(false)
    var role = mutableStateOf<String?>(null)
    var token = mutableStateOf<String?>(null) // <-- Token de sesión

    fun onLoginClick(roleInput: String = "user") {

        if (username.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = "Els camps no poden estar buits"
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = repository.login(username.value, password.value, roleInput)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        isLoggedIn.value = true
                        role.value = body.role
                        token.value = body.token
                    } else {
                        errorMessage.value = "Resposta del servidor buida"
                    }
                } else {
                    when (response.code()) {
                        400 -> errorMessage.value = "Sol·licitud no vàlida"
                        401 -> errorMessage.value = "Usuari o contrasenya incorrectes"
                        else -> errorMessage.value = "Error desconegut: ${response.code()}"
                    }
                }
            } catch (e: Exception) {
                errorMessage.value = "No s'ha pogut connectar amb el servidor"
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            try {
                token.value?.let { sessionToken ->
                    repository.logout(sessionToken)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = "Error al logout: ${e.message}"
            } finally {
                // Reset UI
                isLoggedIn.value = false
                role.value = null
                token.value = null
                username.value = ""
                password.value = ""
                isLoading.value = false
                errorMessage.value = null
            }
        }
    }
}