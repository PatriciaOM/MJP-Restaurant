package com.mjprestaurant.model.userTests;

import com.mjprestaurant.model.user.User;
import com.mjprestaurant.model.user.UserCreateInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserCreateInfoTests {

    @Test
    void testConstructorAndGetters() {
        User user = new User("patricia", "12345", "ADMIN");
        UserCreateInfo info = new UserCreateInfo("tokenXYZ", user);

        assertEquals("tokenXYZ", info.getSessionToken());
        assertEquals(user, info.getUser());
    }

    @Test
    void testSetters() {
        User user1 = new User("u1", "pass1");
        UserCreateInfo info = new UserCreateInfo("token1", user1);

        User user2 = new User("u2", "pass2");
        info.setUser(user2);
        info.setSessionToken("token2");

        assertEquals("token2", info.getSessionToken());
        assertEquals(user2, info.getUser());
    }


}
