package com.mjprestaurant.model.userTests;

import com.mjprestaurant.model.user.User;
import com.mjprestaurant.model.user.UserUpdateInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserUpdateInfoTests {

    @Test
    void testConstructorAndGetters() {
        User user = new User("pepe", "1234");
        UserUpdateInfo info = new UserUpdateInfo("token123", user);

        assertEquals("token123", info.getSessionToken(), "El token hauria de coincidir");
        assertEquals(user, info.getUser(), "L'usuari hauria de coincidir");
    }

    @Test
    void testSetUser() {
        UserUpdateInfo info = new UserUpdateInfo("token123", new User("a", "b"));
        User newUser = new User("luis", "5678");
        info.setUser(newUser);

        assertEquals(newUser, info.getUser(), "L'usuari actualitzat hauria de coincidir");
    }

    @Test
    void testSetSessionToken() {
        UserUpdateInfo info = new UserUpdateInfo("token123", new User("x", "y"));
        info.setSessionToken("newToken");

        assertEquals("newToken", info.getSessionToken(), "El token actualitzat hauria de coincidir");
    }
}
