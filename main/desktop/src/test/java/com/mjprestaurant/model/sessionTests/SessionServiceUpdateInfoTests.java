package com.mjprestaurant.model.sessionTests;

import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.session.SessionService;
import com.mjprestaurant.model.session.SessionServiceUpdateInfo;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SessionServiceUpdateInfoTests {

    @Test
    void testDefaultConstructor() {
        SessionServiceUpdateInfo info = new SessionServiceUpdateInfo();
        assertNull(info.getSessionToken(), "El token debería ser null");
        assertNull(info.getItem(), "El objeto SessionService debería ser null");
    }

    @Test
    void testFullConstructor() {
        SessionService session = new SessionService(1L, 1L, 1, 4, 2, 3, LocalDateTime.now(), LocalDateTime.now().plusHours(1), SessionService.SessionServiceStatus.OPEN, 5, "Comentario");
        SessionServiceUpdateInfo info = new SessionServiceUpdateInfo("token123", session);

        assertEquals("token123", info.getSessionToken());
        assertEquals(session, info.getItem());
    }

    @Test
    void testSettersAndGetters() {
        SessionServiceUpdateInfo info = new SessionServiceUpdateInfo();
        SessionService session = new SessionService(2L, 2L, 2, 6, 4, 2, LocalDateTime.now(), LocalDateTime.now().plusHours(2), SessionService.SessionServiceStatus.CLOSED, 4, "Comentario2");

        info.setSessionToken("tokenABC");
        info.setItem(session);

        assertEquals("tokenABC", info.getSessionToken());
        assertEquals(session, info.getItem());
    }
}
