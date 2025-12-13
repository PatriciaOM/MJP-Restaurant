package com.mjprestaurant.model.tableTests;

import com.mjprestaurant.model.table.TableGetInfo;
import com.mjprestaurant.model.table.TableGetInfo.SearchType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para TableGetInfo
 */
public class TableGetInfoTests {

    @Test
    void testDefaultConstructor() {
        TableGetInfo info = new TableGetInfo();
        assertNull(info.getSessionToken(), "Token debería ser null");
        assertEquals(-1, info.getId(), "ID por defecto debe ser -1");
        assertEquals(-1, info.getNumber(), "Number por defecto debe ser -1");
        assertEquals(SearchType.ALL, info.getSearchType(), "SearchType por defecto debe ser ALL");
    }

    @Test
    void testConstructorAll() {
        TableGetInfo info = new TableGetInfo("token123");
        assertEquals("token123", info.getSessionToken());
        assertEquals(SearchType.ALL, info.getSearchType());
        assertEquals(-1, info.getId());
        assertEquals(-1, info.getNumber());
    }

    @Test
    void testConstructorById() {
        TableGetInfo info = new TableGetInfo("tokenABC", 42L);
        assertEquals("tokenABC", info.getSessionToken());
        assertEquals(42L, info.getId());
        assertEquals(SearchType.BY_ID, info.getSearchType());
        assertEquals(-1, info.getNumber());
    }

    @Test
    void testConstructorByNumber() {
        TableGetInfo info = new TableGetInfo("tokenXYZ", 5);
        assertEquals("tokenXYZ", info.getSessionToken());
        assertEquals(5, info.getNumber());
        assertEquals(SearchType.BY_NUMBER, info.getSearchType());
        assertEquals(-1, info.getId());
    }

    @Test
    void testSettersAndGetters() {
        TableGetInfo info = new TableGetInfo();

        info.setSessionToken("tokenTest");
        info.setId(7L);
        info.setNumber(3);
        info.setSearchType(SearchType.BY_ID);

        assertEquals("tokenTest", info.getSessionToken());
        assertEquals(7L, info.getId());
        assertEquals(3, info.getNumber());
        assertEquals(SearchType.BY_ID, info.getSearchType());
    }

}
