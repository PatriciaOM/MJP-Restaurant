package com.mjprestaurant.model;

import com.mjprestaurant.model.user.User;
import com.mjprestaurant.model.user.UserCreateResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserCreateResponseTests {

    @Test
    void testDefaultConstructor() {
        UserCreateResponse response = new UserCreateResponse();
        assertNull(response.getUser(), "El usuario debería ser null en el constructor por defecto");
    }

    @Test
    void testConstructorWithMessageAndUser() {
        User user = new User("patricia", "12345", "ADMIN");
        UserCreateResponse response = new UserCreateResponse("ok", user);

        assertEquals("ok", response.message, "El mensaje debería coincidir");
        assertEquals(user, response.getUser(), "El usuario debería coincidir con el pasado al constructor");
    }

    @Test
    void testConstructorWithUserOnly() {
        User user = new User("patricia", "12345", "ADMIN");
        UserCreateResponse response = new UserCreateResponse(user);

        assertEquals(user, response.getUser(), "El usuario debería coincidir con el pasado al constructor");
        assertEquals("success", response.message, "El mensaje debería ser 'success' por defecto");
    }

    @Test
    void testSetUser() {
        User user1 = new User("u1", "pass1");
        UserCreateResponse response = new UserCreateResponse();
        response.setUser(user1);

        assertEquals(user1, response.getUser(), "El setter debería actualizar el usuario");
    }

}
