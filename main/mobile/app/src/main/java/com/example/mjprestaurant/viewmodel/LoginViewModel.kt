package com.example.mjprestaurant.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.mjprestaurant.network.AuthRepository

/**
 * ViewModel per a gestionar l'aunteticació i estat del login de l'aplicació.
 *
 *Aquesta classe s'encarrega de:
 * -Gestionar els camps d'usuari i contrasenya
 * -Controlar l'estat de càrrega durant les peticions
 * -Gestionar errors d'autenticació
 * -Mantenir l'estat de sessió de l'usuari
 *
 * @property repository Repositori d'autenticació per a comunicar-se amb el servidor.
 * @property username estat observable del nom d'usuari
 * @property password estat observable de la contrasenya
 * @property isLoading estat observableque indica si hi ha una petició en curs
 * @property errorMessage estat observable per al maneig d'errors
 * @property isLoggedIn estat observable per indicar si l'usuari ha iniciat sessió
 * @property role estat observable amb el rol d'usuari aunteticat
 * @property token estat observable amb el token de sessió
 *
 * @author Martin Muñoz Pozuelo
 */
class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    // Campos de texto
    /**
     * Estat observable per al nom d'usuari al formulari de login.
     * S'actualitza automàticament quan l'usuari escriu al camp corresponent.
     */
    var username = mutableStateOf("")

    /**
     * Estat observable per a la cntrasenya del formulari de login.
     * Utilitza transformació visual per no deixar veure els caràcters.
     */
    var password = mutableStateOf("")

    // Estats UI
    /**
     * Indica si hi ha una petició d'autenticació en curs.
     * Si es true, deshabilita els butons.
     */
    var isLoading = mutableStateOf(false)

    /**
     * Missatge d'error a mostrar a l'usuari en cas d'error a l'autenticació.
     * Es null si no hi ha error
     */
    var errorMessage = mutableStateOf<String?>(null)

    /**
     * Indica si l'usuari té una sessió activa actualment.
     * Controla la navegació entre pantalles.
     */
    var isLoggedIn = mutableStateOf(false)

    /**
     * Rol del'usuari autenticat.
     * Null si no hi ha sessió activa.
     */
    var role = mutableStateOf<String?>(null)

    /**
     * Token de sessió rebut pel servidor tras el login exitos.
     * S'utilitza per a requests autenticats i logout.
     *
     * @sample "session_token_abc123"
     */
    var token = mutableStateOf<String?>(null)

    /**
     * Maneig de l'event del click al prèmer login.
     *
     * Realitza les següents validacions:
     * 1. Valida que els camps no esten buits.
     * 2. Inicia el estat de càrrega.
     * 3. Avisa al repositori per autenticar.
     * 4. Maneja la resposa del servidor.
     * 5. Actualitza l'estat de l'aplicació.
     *
     * @param roleInput Rol de l'usuwari per a autenticació ("user" per defecte)
     * @throws Exception en cas d'error de xarxa o servidor no disponible.
     */
    fun onLoginClick(roleInput: String = "user") {
        //Validació de camps buits
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
                    //Maneig d'errors HTTP
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


    /**
     * Maneig de l'event del tancament de sessió.
     *
     * Realitza les següents accions:
     * 1. Envia petició de logout al servidor (Si hi ha token).
     * 2. Reseteja tots els estats a valors inicials.
     * 3. Maneig d'errors durant el logout.
     */
    fun onLogoutClick() {
        viewModelScope.launch {
            try {
                //Intenta logout si hi ha token.
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