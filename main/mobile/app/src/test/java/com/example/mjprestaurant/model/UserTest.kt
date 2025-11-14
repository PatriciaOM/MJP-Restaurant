package com.example.mjprestaurant.model

import com.example.mjprestaurant.model.user.User
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Proves unitaries per la classe User.
 *
 * Verifica la correcta creació i comportament del model User.
 *
 * @see com.example.mjprestaurant.model.user.User
 */
class UserTest {

    @Test
    fun userShouldBeCreatedWithCorrectUsernameAndRole() {
        // Arrange
        val testUsername = "usuarioProva"
        val testRole = "cambrer"

        // Act
        val user = User(testUsername, testRole)

        // Assert
        assertThat(user.username).isEqualTo(testUsername)
        assertThat(user.role).isEqualTo(testRole)
    }

    /**
     * Prova que dos usuaris amb les mateixes dades son iguals
     * Given: Dos instancias de User amb mateixes dades
     * When: Es comparen
     * Then: Han de ser considerats iguals
     */
    @Test
    fun usersWithSameDataShouldBeEqual() {
        // Arrange
        val user1 = User("mateixUsuari", "mateixRol")
        val user2 = User("mateixUsuari", "mateixRol")

        // Act & Assert
        assertThat(user1).isEqualTo(user2)
    }
    /**
     * Prova de que usuaris amb dades diferents no son iguals
     *
     * Given: Dos instancias de User amb dades diferents
     * When: Es comparen amb equals.
     * Then: No han de ser considerades iguals
     */
    @Test
    fun usersWithDifferentDataShouldNotBeEqual() {
        // Arrange
        val user1 = User("usuari1", "admin")
        val user2 = User("usuari2", "user")

        // Act & Assert
        assertThat(user1).isNotEqualTo(user2)
    }
}