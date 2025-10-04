package com.example.mjprestaurant.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {

    //Camps de texto
    var username = mutableStateOf("")
    var password = mutableStateOf("")

    //Estats de la UI
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)
    var isLoggedIn = mutableStateOf(false)
    var role = mutableStateOf<String?>(null)

    fun onLoginClick(){

        if  (username.value.isBlank() || password.value.isBlank()){
            errorMessage.value = "Els camps no poden estar buits"
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            delay(1000) //Simulem la trucada al servidor

            //Lògica de prova:
            if (username.value == "admin" && password.value == "1234"){
                isLoggedIn.value = true
                role.value = "admin"
            } else if (username.value == "user" && password.value == "1234"){
                isLoggedIn.value = true
                role.value = "user"
            } else {
                errorMessage.value = "Usuari o contrasenya incorrectes"
            }

            isLoading.value = false

        }
    }


    fun onLogoutClick(){
        isLoggedIn.value = false
        role.value = null
        username.value = ""
        password.value = ""
        isLoading.value = false
        errorMessage.value = null
    }

}