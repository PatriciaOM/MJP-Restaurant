package com.example.mjprestaurant.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Prova unitaria per clase LoginRequest.
 *
 * Verifica la correcta creació de l'objecte de petició de login
 *
 * @see LoginRequest
 */
class LoginRequestTest {

    /**
     * Prova que LoginRequest es crea amb totes les credencials necessaries.
     *
     * Given: username, password i rol vàlids.
     * When: Es crea LoginRequest
     * Then: Totes les propietats han de tenir valors correctes.
     */
    @Test
    fun logicRequestShouldContainUsernamePasswordAndRole() {
        // Arrange
        val username = "testUser"
        val password = "testPassword123"
        val role = "user"

        // Act
        val loginRequest = LoginRequest(username, password, role)

        // Assert
        assertThat(loginRequest.username).isEqualTo(username)
        assertThat(loginRequest.password).isEqualTo(password)
        assertThat(loginRequest.role).isEqualTo(role)
    }

    /**
     * Prova igualtat de LoginRequests amb mateixes dades.
     */
    @Test
    fun loginRequestsWithSameCredentialsShouldBeEqual() {
        // Arrange
        val request1 = LoginRequest("user", "pass", "role")
        val request2 = LoginRequest("user", "pass", "role")

        // Act & Assert
        assertThat(request1).isEqualTo(request2)
    }
}