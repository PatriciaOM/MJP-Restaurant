package com.example.mjprestaurant.model

import com.example.mjprestaurant.model.auth.LoginResponse
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Proves unitaries per la clase LoginResponse
 *
 * Verificar la correcta creació de la resposta del Login.
 *
 * @see com.example.mjprestaurant.model.auth.LoginResponse
 */
class LoginResponseTest {

    /**
     * Prova que LoginResponse es crea amb token i rol
     *
     * Given: Token i rol vàlids
     * When: Es crea LoginResponse
     * Then: Ha de tenir token i rol proporcionats.
     */
    @Test
    fun loginResponseShouldContainTokenAndRole() {
        // Arrange
        val testToken = "session_token_abc123"
        val testRole = "admin"

        // Act
        val loginResponse = LoginResponse(testToken, testRole)

        // Assert
        assertThat(loginResponse.token).isEqualTo(testToken)
        assertThat(loginResponse.role).isEqualTo(testRole)
    }

    /**
     * Prova que dos LoginResponse amb mateixes dades son iguals
     */
    @Test
    fun loginResponsesWithSameDataShouldBeEqual() {
        // Arrange
        val response1 = LoginResponse("token1", "role1")
        val response2 = LoginResponse("token1", "role1")

        // Act & Assert
        assertThat(response1).isEqualTo(response2)
    }
}