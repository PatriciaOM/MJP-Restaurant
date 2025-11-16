package com.mjprestaurant.model;

import com.mjprestaurant.model.dish.DishDeleteInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para DishDeleteInfo
 */
public class DishDeleteInfoTests {

    @Test
    void testDefaultConstructor() {
        DishDeleteInfo info = new DishDeleteInfo();
        assertNull(info.getSessionToken(), "Token debería ser null");
        assertNull(info.getId(), "Id debería ser null");
    }

    @Test
    void testFullConstructor() {
        DishDeleteInfo info = new DishDeleteInfo("token123", 10L);
        assertEquals("token123", info.getSessionToken());
        assertEquals(10L, info.getId());
    }

    @Test
    void testCopyConstructor() {
        DishDeleteInfo original = new DishDeleteInfo("tokenABC", 42L);
        DishDeleteInfo copy = new DishDeleteInfo(original);
        assertEquals("tokenABC", copy.getSessionToken());
        // Observa que el constructor de copia solo copia el token, no el id
        assertNull(copy.getId(), "Id debería ser null en copia porque el constructor solo copia token");
    }

    @Test
    void testSettersAndGetters() {
        DishDeleteInfo info = new DishDeleteInfo();
        info.setSessionToken("tokenXYZ");
        info.setId(99L);

        assertEquals("tokenXYZ", info.getSessionToken());
        assertEquals(99L, info.getId());
    }


}
