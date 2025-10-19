package com.example.mjprestaurant.viewmodel

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Proves unitaries amb LoginViewModel
 *
 * Verifica la lògica de negoci i maneig d'estats del ViewModel.
 *
 * @see LoginViewModel
 */
class LoginViewModelTest {

    /**
     * Prova que camps buits mostren error i no truquen servidor.
     *
     * Given: Camps d'username i password buits.
     * When: S'intenta el login.
     * Then: Ha de mostrar error i no canviar estat.
     */
    @Test
    fun loginWithEmptyFieldsShouldShowError() {
        // Arrange
        val viewModel = LoginViewModel()
        viewModel.username.value = ""
        viewModel.password.value = ""

        // Act
        viewModel.onLoginClick()

        // Assert
        assertThat(viewModel.errorMessage.value).isEqualTo("Els camps no poden estar buits")
        assertThat(viewModel.isLoading.value).isFalse()
    }

    /**
     * Prova que username buit i password ple mostra error.
     *
     * Given: username buit i password amb valor.
     * When: s'intenta el login
     * Then: ha de mostrar error als camps buits.
     */
    @Test
    fun loginWithEmptyUsernameShouldShowError() {
        // Arrange
        val viewModel = LoginViewModel()
        viewModel.username.value = ""
        viewModel.password.value = "password123"

        // Act
        viewModel.onLoginClick()

        // Assert
        assertThat(viewModel.errorMessage.value).isEqualTo("Els camps no poden estar buits")
    }

    /**
     * Prova de password buit i username ple mostra error
     *
     * Given: Username amb valor i password buit
     * When: S'intenta fer login
     * Then: Ha de mostrar error de camps buits.
     */
    @Test
    fun loginWithEmptyPasswordShouldShowError() {
        // Arrange
        val viewModel = LoginViewModel()
        viewModel.username.value = "usuario"
        viewModel.password.value = ""

        // Act
        viewModel.onLoginClick()

        // Assert
        assertThat(viewModel.errorMessage.value).isEqualTo("Els camps no poden estar buits")
    }
}