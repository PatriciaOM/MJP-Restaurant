package com.mjprestaurant.model;

import com.mjprestaurant.model.table.TableGetResponse;
import com.mjprestaurant.model.table.TableRestaurant;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para TableGetResponse
 */
public class TableGetResponseTests {

    @Test
    void testDefaultConstructor() {
        TableGetResponse response = new TableGetResponse();
        assertNull(response.getTables(), "La lista de tablas por defecto debe ser null");
    }

    @Test
    void testConstructorWithList() {
        List<TableRestaurant> tables = new ArrayList<>();
        tables.add(new TableRestaurant(1, 4));
        tables.add(new TableRestaurant(2, 6));

        TableGetResponse response = new TableGetResponse(tables);
        assertEquals(2, response.getTables().size(), "Debe contener 2 tablas");
        assertEquals(1, response.getTables().get(0).getNum(), "Primera tabla número correcto");
        assertEquals(6, response.getTables().get(1).getMaxGuests(), "Segunda tabla maxGuests correcto");
    }

    @Test
    void testSetAndGetTables() {
        TableGetResponse response = new TableGetResponse();

        List<TableRestaurant> tables = new ArrayList<>();
        tables.add(new TableRestaurant(3, 5));

        response.setTables(tables);
        assertNotNull(response.getTables(), "La lista no debe ser null después de setTables");
        assertEquals(1, response.getTables().size(), "Debe contener 1 tabla");
        assertEquals(3, response.getTables().get(0).getNum(), "Número de tabla correcto");
    }

}
