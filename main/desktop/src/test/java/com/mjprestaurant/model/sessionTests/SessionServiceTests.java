package com.mjprestaurant.model.sessionTests;

import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.session.SessionService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SessionServiceTests {

    @Test
    void testDefaultConstructor() {
        SessionService session = new SessionService();
        assertNull(session.getId());
        assertEquals(0, session.getNumTable());
        assertNull(session.getIdTable());
        assertEquals(0, session.getMaxClients());
        assertEquals(0, session.getWaiterId());
        assertEquals(0, session.getClients());
        assertNull(session.getStartDate());
        assertNull(session.getEndDate());
        assertNull(session.getStatus());
        assertEquals(0, session.getRating());
        assertNull(session.getComment());
    }

    @Test
    void testFullConstructor() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(2);

        SessionService session = new SessionService(
                1L, 10L, 5, 10, 100, 3, start, end, SessionService.SessionServiceStatus.OPEN, 4, "Buen servicio"
        );

        assertEquals(1L, session.getId());
        assertEquals(10L, session.getIdTable());
        assertEquals(5, session.getNumTable());
        assertEquals(10, session.getMaxClients());
        assertEquals(100, session.getWaiterId());
        assertEquals(3, session.getClients());
        assertEquals(start, session.getStartDate());
        assertEquals(end, session.getEndDate());
        assertEquals(SessionService.SessionServiceStatus.OPEN, session.getStatus());
        assertEquals(4, session.getRating());
        assertEquals("Buen servicio", session.getComment());
    }

    @Test
    void testConstructorWithoutId() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(2);

        SessionService session = new SessionService(
                10L, 5, 10, 100, 3, start, end, SessionService.SessionServiceStatus.CLOSED, 5, "Comentario"
        );

        assertNull(session.getId());
        assertEquals(10L, session.getIdTable());
        assertEquals(5, session.getNumTable());
        assertEquals(10, session.getMaxClients());
        assertEquals(100, session.getWaiterId());
        assertEquals(3, session.getClients());
        assertEquals(start, session.getStartDate());
        assertEquals(end, session.getEndDate());
        assertEquals(SessionService.SessionServiceStatus.CLOSED, session.getStatus());
        assertEquals(5, session.getRating());
        assertEquals("Comentario", session.getComment());
    }

    @Test
    void testCopyConstructor() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);

        SessionService original = new SessionService(
                2L, 20L, 6, 11, 101, 4, start, end, SessionService.SessionServiceStatus.PAID, 3, "Original"
        );

        SessionService copy = new SessionService(original);

        assertEquals(original.getId(), copy.getId());
        assertEquals(original.getIdTable(), copy.getIdTable());
        assertEquals(original.getNumTable(), copy.getNumTable());
        assertEquals(original.getMaxClients(), copy.getMaxClients());
        assertEquals(original.getWaiterId(), copy.getWaiterId());
        assertEquals(original.getClients(), copy.getClients());
        assertEquals(original.getStartDate(), copy.getStartDate());
        assertEquals(original.getEndDate(), copy.getEndDate());
        assertEquals(original.getStatus(), copy.getStatus());
        assertEquals(original.getRating(), copy.getRating());
        assertEquals(original.getComment(), copy.getComment());
    }

    @Test
    void testSettersAndGetters() {
        SessionService session = new SessionService();

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);

        session.setId(5L);
        session.setIdTable(50L);
        session.setNumTable(7);
        session.setMaxClients(12);
        session.setWaiterId(200);
        session.setClients(6);
        session.setStartDate(start);
        session.setEndDate(end);
        session.setStatus(SessionService.SessionServiceStatus.OPEN);
        session.setRating(4);
        session.setComment("Test");

        assertEquals(5L, session.getId());
        assertEquals(50L, session.getIdTable());
        assertEquals(7, session.getNumTable());
        assertEquals(12, session.getMaxClients());
        assertEquals(200, session.getWaiterId());
        assertEquals(6, session.getClients());
        assertEquals(start, session.getStartDate());
        assertEquals(end, session.getEndDate());
        assertEquals(SessionService.SessionServiceStatus.OPEN, session.getStatus());
        assertEquals(4, session.getRating());
        assertEquals("Test", session.getComment());
    }
}
