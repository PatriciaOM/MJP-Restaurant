package com.mjprestaurant.model;

import com.mjprestaurant.model.dish.Dish;
import com.mjprestaurant.model.dish.Dish.DishCategory;
import com.mjprestaurant.model.dish.DishGetInfo;
import com.mjprestaurant.model.dish.DishGetInfo.SearchType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para DishGetInfo
 */
public class DishGetInfoTests {

    @Test
    void testDefaultConstructor() {
        DishGetInfo info = new DishGetInfo();
        assertNull(info.getSessionToken());
        assertNull(info.getDish());
        assertNull(info.getId());
        assertNull(info.getName());
        assertNull(info.getSearchType());
    }

    @Test
    void testConstructorAll() {
        DishGetInfo info = new DishGetInfo("token123");
        assertEquals("token123", info.getSessionToken());
        assertEquals(SearchType.ALL, info.getSearchType());
    }

    @Test
    void testConstructorById() {
        DishGetInfo info = new DishGetInfo("tokenABC", 42L);
        assertEquals("tokenABC", info.getSessionToken());
        assertEquals(42L, info.getId());
        assertEquals(SearchType.BY_ID, info.getSearchType());
    }

    @Test
    void testConstructorByName() {
        DishGetInfo info = new DishGetInfo("tokenXYZ", "Pizza");
        assertEquals("tokenXYZ", info.getSessionToken());
        assertEquals("Pizza", info.getName());
        assertEquals(SearchType.BY_NAME, info.getSearchType());
    }

    @Test
    void testFullConstructor() {
        Dish dish = new Dish("Sopa", 3.5f, "Sopa caliente", true, DishCategory.APPETIZER);
        DishGetInfo info = new DishGetInfo("token999", dish, SearchType.BY_NAME, 10L, "Sopa");

        assertEquals("token999", info.getSessionToken());
        assertEquals(dish, info.getDish());
        assertEquals(SearchType.BY_NAME, info.getSearchType());
        assertEquals(10L, info.getId());
        assertEquals("Sopa", info.getName());
    }

    @Test
    void testCopyConstructor() {
        Dish dish = new Dish("Ensalada", 5.0f, "Ensalada fresca", false, DishCategory.APPETIZER);
        DishGetInfo original = new DishGetInfo("tokenCopy", dish, SearchType.ALL, 5L, "Ensalada");

        DishGetInfo copy = new DishGetInfo(original);
        assertEquals("tokenCopy", copy.getSessionToken());
        assertEquals(dish, copy.getDish());
        assertEquals(SearchType.ALL, copy.getSearchType());
        assertEquals(5L, copy.getId());
        assertEquals("Ensalada", copy.getName());
    }

    @Test
    void testSettersAndGetters() {
        DishGetInfo info = new DishGetInfo();
        Dish dish = new Dish("Hamburguesa", 8.5f, "Con queso y bacon", true, DishCategory.MAIN);

        info.setSessionToken("tokenAAA");
        info.setDish(dish);
        info.setMessageData(dish); // debería afectar también a getDish/getMessageData

        assertEquals("tokenAAA", info.getSessionToken());
        assertEquals(dish, info.getDish());
        assertEquals(dish, info.getMessageData());
    }


}
