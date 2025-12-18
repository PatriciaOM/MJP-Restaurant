package com.mjprestaurant.model.sessionTests;

import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.session.SessionService;
import com.mjprestaurant.model.session.SessionServiceGetResponse;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SessionServiceGetResponseTests {

    @Test
    void testDefaultConstructor() {
        SessionServiceGetResponse response = new SessionServiceGetResponse();
        assertNull(response.getMessageStatus(), "El mensaje de estado debería ser null");
        assertNull(response.getSessionServices(), "La lista de sesiones debería ser null");
    }

    @Test
    void testFullConstructor() {
        List<SessionService> list = new ArrayList<>();
        SessionService session = new SessionService();
        list.add(session);

        SessionServiceGetResponse response = new SessionServiceGetResponse("OK", list);

        assertEquals("OK", response.getMessageStatus());
        assertEquals(list, response.getSessionServices());
        assertEquals(1, response.getSessionServices().size());
        assertEquals(session, response.getSessionServices().get(0));
    }

    @Test
    void testSettersAndGetters() {
        SessionServiceGetResponse response = new SessionServiceGetResponse();
        List<SessionService> list = new ArrayList<>();
        SessionService session = new SessionService();
        list.add(session);

        response.setMessageStatus("SUCCESS");
        response.setSessionServices(list);

        assertEquals("SUCCESS", response.getMessageStatus());
        assertEquals(list, response.getSessionServices());
        assertEquals(1, response.getSessionServices().size());
        assertEquals(session, response.getSessionServices().get(0));
    }
}
