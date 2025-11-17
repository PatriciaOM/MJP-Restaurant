package com.mjprestaurant.model;

import com.mjprestaurant.model.table.TableRestaurant;
import com.mjprestaurant.model.table.TableUpdateInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para TableUpdateInfo
 */
public class TableUpdateInfoTests {

    @Test
    void testDefaultConstructor() {
        TableUpdateInfo info = new TableUpdateInfo();
        assertNull(info.getSessionToken(), "Token por defecto debe ser null");
        assertNull(info.getTable(), "Table por defecto debe ser null");
    }

    @Test
    void testConstructorWithParameters() {
        TableRestaurant table = new TableRestaurant(3, 6);
        TableUpdateInfo info = new TableUpdateInfo("abc123", table);

        assertEquals("abc123", info.getSessionToken(), "Token debe ser 'abc123'");
        assertEquals(table, info.getTable(), "Table debe ser la misma instancia pasada");
    }

    @Test
    void testSetAndGetSessionToken() {
        TableUpdateInfo info = new TableUpdateInfo();
        info.setSessionToken("tokenXYZ");
        assertEquals("tokenXYZ", info.getSessionToken(), "Token debe ser 'tokenXYZ'");
    }

    @Test
    void testSetAndGetTable() {
        TableRestaurant table = new TableRestaurant(5, 8);
        TableUpdateInfo info = new TableUpdateInfo();
        info.setTable(table);
        assertEquals(table, info.getTable(), "Table debe ser la misma instancia asignada");
    }

    @Test
    void testMessageDataMethods() {
        TableRestaurant table = new TableRestaurant(7, 4);
        TableUpdateInfo info = new TableUpdateInfo();
        info.setMessageData(table);

        assertEquals(table, info.getMessageData(), "getMessageData debe devolver la misma instancia que se pasó");
    }

}
