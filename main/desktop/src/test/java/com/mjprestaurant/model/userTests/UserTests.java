package com.mjprestaurant.model.userTests;

import com.mjprestaurant.model.user.User;
import com.mjprestaurant.model.user.UserShift;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para la clase User
 */
public class UserTests {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRole());
        assertNull(user.getName());
        assertNull(user.getSurname());
        assertNull(user.getShift());
        assertNull(user.getStartDate());
        assertNull(user.getEndDate());
        assertNull(user.getDni());
        assertNull(user.getId());
    }

    @Test
    void testConstructorUsernamePassword() {
        User user = new User("patricia", "12345");
        assertEquals("patricia", user.getUsername());
        assertEquals("12345", user.getPassword());
    }

    @Test
    void testConstructorUsernamePasswordRole() {
        User user = new User("admin", "pass", "ADMIN");
        assertEquals("admin", user.getUsername());
        assertEquals("pass", user.getPassword());
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    void testFullConstructorWithoutId() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);
        User user = new User("u", "p", "USER", "Patricia", "Oliva", UserShift.MORNING, start, end, "12345678X");

        assertEquals("u", user.getUsername());
        assertEquals("p", user.getPassword());
        assertEquals("USER", user.getRole());
        assertEquals("Patricia", user.getName());
        assertEquals("Oliva", user.getSurname());
        assertEquals(UserShift.MORNING, user.getShift());
        assertEquals(start, user.getStartDate());
        assertEquals(end, user.getEndDate());
        assertEquals("12345678X", user.getDni());
        assertNull(user.getId());
    }

    @Test
    void testFullConstructorWithId() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);
        User user = new User("u", "p", "USER", "Patricia", "Oliva", UserShift.MORNING, start, end, "12345678X", 42L);

        assertEquals(42L, user.getId());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("pass1");
        user.setRole("ADMIN");
        user.setName("Patricia");
        user.setSurname("Oliva");
        user.setShift(UserShift.MORNING);
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 6, 30);
        user.setStartDate(start);
        user.setEndDate(end);
        user.setDni("87654321Y");
        user.setId(10L);

        assertEquals("user1", user.getUsername());
        assertEquals("pass1", user.getPassword());
        assertEquals("ADMIN", user.getRole());
        assertEquals("Patricia", user.getName());
        assertEquals("Oliva", user.getSurname());
        assertEquals(UserShift.MORNING, user.getShift());
        assertEquals(start, user.getStartDate());
        assertEquals(end, user.getEndDate());
        assertEquals("87654321Y", user.getDni());
        assertEquals(10L, user.getId());
    }

}
