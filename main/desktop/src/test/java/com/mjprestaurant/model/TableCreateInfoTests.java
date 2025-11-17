package com.mjprestaurant.model;

import com.mjprestaurant.model.table.TableCreateInfo;
import com.mjprestaurant.model.table.TableRestaurant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para TableCreateInfo
 */
public class TableCreateInfoTests {

    @Test
    void testDefaultConstructor() {
        TableCreateInfo info = new TableCreateInfo();
        assertNull(info.getSessionToken(), "El token debería ser null");
        assertNull(info.getTable(), "La tabla debería ser null");
    }

    @Test
    void testFullConstructor() {
        TableRestaurant table = new TableRestaurant(1, 4); // num=1, maxGuests=4
        TableCreateInfo info = new TableCreateInfo("token123", table);

        assertEquals("token123", info.getSessionToken());
        assertEquals(table, info.getTable());
        assertEquals(1, info.getTable().getNum());
        assertEquals(4, info.getTable().getMaxGuests());
    }

    @Test
    void testCopyConstructor() {
        TableRestaurant table = new TableRestaurant(2, 6);
        TableCreateInfo original = new TableCreateInfo("tokenABC", table);

        TableCreateInfo copy = new TableCreateInfo(original);
        assertEquals("tokenABC", copy.getSessionToken());
        assertEquals(table, copy.getTable());
        assertEquals(2, copy.getTable().getNum());
        assertEquals(6, copy.getTable().getMaxGuests());
    }

    @Test
    void testSettersAndGetters() {
        TableCreateInfo info = new TableCreateInfo();
        TableRestaurant table = new TableRestaurant(3, 2);

        info.setSessionToken("tokenXYZ");
        info.setTable(table);

        assertEquals("tokenXYZ", info.getSessionToken());
        assertEquals(table, info.getTable());
        assertEquals(3, info.getTable().getNum());
        assertEquals(2, info.getTable().getMaxGuests());
    }
}
