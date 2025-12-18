package com.example.mjprestaurant.model

import com.example.mjprestaurant.model.session.SessionService
import com.example.mjprestaurant.model.session.SessionStatus
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Proves unitàries per al model SessionService.
 *
 * Verifica la integritat de les dades de la sessió d'una taula.
 *
 * @see SessionService
 * @author Martin Muñoz Pozuelo
 */
class SessionServiceTest {

    /**
     * Prova que una sessió es crea amb l'estat correcte (OPEN).
     *
     * Given: Dades per a una nova sessió.
     * When: S'instancia l'objecte.
     * Then: L'estat ha de ser OPEN i les dades coincidir.
     */
    @Test
    fun sessionShouldBeCreatedWithOpenStatus() {
        // Arrange
        val tableId = 1L
        val diners = 4
        val date = "2025-11-17T14:00:00"

        // Act
        val session = SessionService(
            idTable = tableId,
            numTable = 1,
            maxClients = 6,
            waiterId = 1,
            clients = diners,
            startDate = date,
            status = SessionStatus.OPEN
        )

        // Assert
        assertThat(session.status).isEqualTo(SessionStatus.OPEN)
        assertThat(session.clients).isEqualTo(diners)
        assertThat(session.idTable).isEqualTo(tableId)
    }

    /**
     * Prova d'igualtat entre dues sessions idèntiques.
     */
    @Test
    fun sessionsWithSameDataShouldBeEqual() {
        // Arrange
        val s1 = SessionService(1L, 1, 1, 4, 1, 2, "date", null, SessionStatus.OPEN)
        val s2 = SessionService(1L, 1, 1, 4, 1, 2, "date", null, SessionStatus.OPEN)

        // Act & Assert
        assertThat(s1).isEqualTo(s2)
    }
}