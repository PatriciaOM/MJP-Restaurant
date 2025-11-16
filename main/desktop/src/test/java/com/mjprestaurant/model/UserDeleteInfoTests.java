package com.mjprestaurant.model;

import com.mjprestaurant.model.user.UserDeleteInfo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserDeleteInfoTests {

    @Test
    void testConstructorAndGetters() {
        UserDeleteInfo info = new UserDeleteInfo("token123", "patricia");

        assertEquals("patricia", info.getUserName(), "El username debería coincidir con el pasado al constructor");
        assertEquals("token123", info.getSessionToken(), "El token debería coincidir con el pasado al constructor");
    }

    @Test
    void testSetters() {
        UserDeleteInfo info = new UserDeleteInfo("token123", "patricia");

        info.setUserName("juan");
        info.setSessionToken("token456");

        assertEquals("juan", info.getUserName(), "El setter debería actualizar el username");
        assertEquals("token456", info.getSessionToken(), "El setter debería actualizar el token");
    }

}
