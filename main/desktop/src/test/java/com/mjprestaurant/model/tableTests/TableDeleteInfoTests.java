package com.mjprestaurant.model.tableTests;

import com.mjprestaurant.model.table.TableDeleteInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para TableDeleteInfo
 */
public class TableDeleteInfoTests {

    @Test
    void testDefaultConstructor() {
        TableDeleteInfo info = new TableDeleteInfo();
        assertNull(info.getSessionToken(), "El token debería ser null");
        assertNull(info.getId(), "El id debería ser null");
    }

    @Test
    void testFullConstructor() {
        TableDeleteInfo info = new TableDeleteInfo("token123", 10L);
        assertEquals("token123", info.getSessionToken());
        assertEquals(10L, info.getId());
    }

    @Test
    void testSettersAndGetters() {
        TableDeleteInfo info = new TableDeleteInfo();

        info.setSessionToken("tokenXYZ");
        info.setId(5L);

        assertEquals("tokenXYZ", info.getSessionToken());
        assertEquals(5L, info.getId());
    }

}
