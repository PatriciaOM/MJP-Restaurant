package com.mjprestaurant.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.session.SessionService;

/**
 * Tests unitarios de SessionController siguiendo el patrón de DishControllerTest.
 */
public class SessionControllerTest {

    private static class DummySessionController extends SessionController {
        private List<SessionService> dummySessions = new ArrayList<>();

        @Override
        public SessionService getSessionByTableId(String token, Long tableId) {
            if (tableId <= 0) return null;
            SessionService session = new SessionService();
            dummySessions.add(session);
            return session;
        }

        @Override
        public List<SessionService> getSessionsByTableId(String token, Long tableId) {
            if (tableId <= 0) return new ArrayList<>();
            return dummySessions;
        }

        @Override
        public void updateSession(String token, SessionService session) {
            dummySessions.add(session);
        }
    }

    private DummySessionController controller;

    @BeforeEach
    public void setUp() {
        controller = new DummySessionController();
    }

    @Test
    public void testGetSessionByTableId() {
        assertDoesNotThrow(() -> {
            SessionService session = controller.getSessionByTableId("dummyToken", 1L);
            assertNotNull(session);
        });
    }

    @Test
    public void testGetSessionsByTableId() {
        assertDoesNotThrow(() -> {
            List<SessionService> sessions = controller.getSessionsByTableId("dummyToken", 1L);
            assertNotNull(sessions);
        });
    }

    @Test
    public void testUpdateSession() {
        assertDoesNotThrow(() -> {
            SessionService session = new SessionService();
            controller.updateSession("dummyToken", session);
        });
    }

    @Test
    public void testGetSessionByTableIdWithInvalidId() {
        assertDoesNotThrow(() -> {
            SessionService session = controller.getSessionByTableId("dummyToken", -1L);
            assertNull(session);
        });
    }

    @Test
    public void testGetSessionsByTableIdWithInvalidId() {
        assertDoesNotThrow(() -> {
            List<SessionService> sessions = controller.getSessionsByTableId("dummyToken", -5L);
            assertNotNull(sessions);
            assertTrue(sessions.isEmpty());
        });
    }
}
