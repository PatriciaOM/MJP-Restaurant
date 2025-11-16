package com.mjprestaurant.model;

import com.mjprestaurant.model.table.TableRestaurant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para TableRestaurant
 */
public class TableRestaurantTests {

    @Test
    void testDefaultConstructor() {
        TableRestaurant table = new TableRestaurant();
        assertEquals(0, table.getNum(), "Número de tabla por defecto debe ser 0");
        assertEquals(0, table.getMaxGuests(), "MaxGuests por defecto debe ser 0");
        assertNull(table.getId(), "Id por defecto debe ser null");
    }

    @Test
    void testConstructorWithParameters() {
        TableRestaurant table = new TableRestaurant(5, 8);
        assertEquals(5, table.getNum(), "Número de tabla debe ser 5");
        assertEquals(8, table.getMaxGuests(), "MaxGuests debe ser 8");
        assertNull(table.getId(), "Id debe ser null al crear con constructor principal");
    }

    @Test
    void testSetAndGetId() {
        TableRestaurant table = new TableRestaurant();
        table.setId(123L);
        assertEquals(123L, table.getId(), "Id debe ser 123");
    }

    @Test
    void testSetAndGetNum() {
        TableRestaurant table = new TableRestaurant();
        table.setNum(10);
        assertEquals(10, table.getNum(), "Número de tabla debe ser 10");
    }

    @Test
    void testSetAndGetMaxGuests() {
        TableRestaurant table = new TableRestaurant();
        table.setMaxGuests(6);
        assertEquals(6, table.getMaxGuests(), "MaxGuests debe ser 6");
    }
}
