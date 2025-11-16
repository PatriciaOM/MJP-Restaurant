package com.mjprestaurant.model;

import com.mjprestaurant.model.user.UserGetInfo;
import com.mjprestaurant.model.user.UserGetInfo.SearchType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserGetInfoTests {

    @Test
    void testConstructorDefault() {
        UserGetInfo info = new UserGetInfo();
        assertEquals(-1, info.getId(), "El id por defecto debe ser -1");
        assertNull(info.getUserName(), "El username por defecto debe ser null");
        assertNull(info.getSessionToken(), "El token por defecto debe ser null");
        assertEquals(SearchType.ALL, info.getSearchType(), "El tipo de búsqueda por defecto debe ser ALL");
    }

    @Test
    void testConstructorAllUsers() {
        UserGetInfo info = new UserGetInfo("token123");
        assertEquals("token123", info.getSessionToken());
        assertEquals(SearchType.ALL, info.getSearchType());
    }

    @Test
    void testConstructorById() {
        UserGetInfo info = new UserGetInfo("token123", 10L);
        assertEquals("token123", info.getSessionToken());
        assertEquals(10L, info.getId());
        assertEquals(SearchType.BY_ID, info.getSearchType());
    }

    @Test
    void testConstructorByUsername() {
        UserGetInfo info = new UserGetInfo("token123", "patricia");
        assertEquals("token123", info.getSessionToken());
        assertEquals("patricia", info.getUserName());
        assertEquals(SearchType.BY_USERNAME, info.getSearchType());
    }

    @Test
    void testSetters() {
        UserGetInfo info = new UserGetInfo();
        info.setUserName("juan");
        info.setSessionToken("token456");
        info.setId(42L);
        info.setSearchType(SearchType.BY_USERNAME);

        assertEquals("juan", info.getUserName());
        assertEquals("token456", info.getSessionToken());
        assertEquals(42L, info.getId());
        assertEquals(SearchType.BY_USERNAME, info.getSearchType());
    }

}
