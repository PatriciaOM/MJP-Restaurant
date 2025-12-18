package com.mjprestaurant.model.sessionTests;

import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.session.SessionService;
import com.mjprestaurant.model.session.SessionServiceResponse;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SessionServiceResponseTests {

    @Test
    void testDefaultConstructor() {
        SessionServiceResponse response = new SessionServiceResponse();
        assertNull(response.getMessageStatus(), "El mensaje de estado debería ser null");
        assertNull(response.getSessionServices(), "La lista de sesiones debería ser null");
    }

    @Test
    void testFullConstructor() {
        List<SessionService> list = new ArrayList<>();
        SessionService session = new SessionService();
        list.add(session);

        SessionServiceResponse response = new SessionServiceResponse("OK", list);

        assertEquals("OK", response.getMessageStatus());
        assertEquals(list, response.getSessionServices());
        assertEquals(1, response.getSessionServices().size());
        assertEquals(session, response.getSessionServices().get(0));
    }

    @Test
    void testCopyConstructor() {
        List<SessionService> list = new ArrayList<>();
        SessionService session = new SessionService();
        list.add(session);

        SessionServiceResponse original = new SessionServiceResponse("OK", list);
        SessionServiceResponse copy = new SessionServiceResponse(original);

        assertEquals("OK", copy.getMessageStatus());
        assertEquals(list, copy.getSessionServices());
        assertEquals(1, copy.getSessionServices().size());
        assertEquals(session, copy.getSessionServices().get(0));
    }

    @Test
    void testSettersAndGetters() {
        SessionServiceResponse response = new SessionServiceResponse();
        List<SessionService> list = new ArrayList<>();
        SessionService session = new SessionService();
        list.add(session);

        response.setMessageStatus("SUCCESS");
        response.setSessionServices(list);

        assertEquals("SUCCESS", response.getMessageStatus());
        assertEquals(list, response.getSessionServices());
        assertEquals(1, response.getSessionServices().size());
        assertEquals(session, response.getSessionServices().get(0));

        // probar setMessageData y getMessageData
        List<SessionService> newList = new ArrayList<>();
        SessionService s2 = new SessionService();
        newList.add(s2);
        response.setMessageData(newList);
        assertEquals(newList, response.getMessageData());
        assertEquals(s2, response.getMessageData().get(0));
    }
}
