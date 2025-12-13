package com.mjprestaurant.model.sessionTests;

import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.session.SessionService;
import com.mjprestaurant.model.session.SessionServiceGetInfo;

import static org.junit.jupiter.api.Assertions.*;

public class SessionServiceGetInfoTests {

    @Test
    void testDefaultConstructor() {
        SessionServiceGetInfo info = new SessionServiceGetInfo();
        assertNull(info.getSessionToken());
        assertNull(info.getSessionService());
        assertNull(info.getId());
        assertNull(info.getSearchType());
    }

    @Test
    void testConstructorAllSessions() {
        SessionServiceGetInfo info = new SessionServiceGetInfo("token123");
        assertEquals("token123", info.getSessionToken());
        assertEquals(SessionServiceGetInfo.SearchType.ALL, info.getSearchType());
    }

    @Test
    void testConstructorById() {
        SessionServiceGetInfo info = new SessionServiceGetInfo("tokenABC", 42L);
        assertEquals("tokenABC", info.getSessionToken());
        assertEquals(42L, info.getId());
        assertEquals(SessionServiceGetInfo.SearchType.BY_ID, info.getSearchType());
    }

    @Test
    void testFullConstructor() {
        SessionService session = new SessionService();
        SessionServiceGetInfo info = new SessionServiceGetInfo("tokenXYZ", session, SessionServiceGetInfo.SearchType.BY_ID, 100L);

        assertEquals("tokenXYZ", info.getSessionToken());
        assertEquals(session, info.getSessionService());
        assertEquals(SessionServiceGetInfo.SearchType.BY_ID, info.getSearchType());
        assertEquals(100L, info.getId());
    }

    @Test
    void testCopyConstructor() {
        SessionService session = new SessionService();
        SessionServiceGetInfo original = new SessionServiceGetInfo("tokenCopy", session, SessionServiceGetInfo.SearchType.ALL, 50L);

        SessionServiceGetInfo copy = new SessionServiceGetInfo(original);

        assertEquals("tokenCopy", copy.getSessionToken());
        assertEquals(session, copy.getSessionService());
        assertEquals(SessionServiceGetInfo.SearchType.ALL, copy.getSearchType());
        assertEquals(50L, copy.getId());
    }

    @Test
    void testSettersAndGetters() {
        SessionServiceGetInfo info = new SessionServiceGetInfo();
        SessionService session = new SessionService();

        info.setSessionToken("tokenSet");
        info.setId(77L);

        assertEquals("tokenSet", info.getSessionToken());
        assertEquals(77L, info.getId());

        info.setMessageData(88L);
        assertEquals(88L, info.getMessageData());
    }
}
