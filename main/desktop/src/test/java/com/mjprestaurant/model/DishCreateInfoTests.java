package com.mjprestaurant.model;

import com.mjprestaurant.model.dish.Dish;
import com.mjprestaurant.model.dish.Dish.DishCategory;
import com.mjprestaurant.model.dish.DishCreateInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para DishCreateInfo
 */
public class DishCreateInfoTests {

    @Test
    void testDefaultConstructor() {
        DishCreateInfo info = new DishCreateInfo();
        assertNull(info.getSessionToken(), "Token debería ser null");
        assertNull(info.getDish(), "Dish debería ser null");
        assertNull(info.getNewEntry(), "NewEntry debería ser null");
    }

    @Test
    void testFullConstructor() {
        Dish dish = new Dish("Pizza", 12.5f, "Pizza de jamón", true, DishCategory.MAIN);
        DishCreateInfo info = new DishCreateInfo("token123", dish);

        assertEquals("token123", info.getSessionToken());
        assertEquals(dish, info.getDish());
        assertEquals(dish, info.getNewEntry());
    }

    @Test
    void testSettersAndGetters() {
        DishCreateInfo info = new DishCreateInfo();
        Dish dish = new Dish("Sopa", 3.5f, "Sopa caliente", true, DishCategory.APPETIZER);

        info.setSessionToken("tokenABC");
        info.setDish(dish);
        assertEquals("tokenABC", info.getSessionToken());
        assertEquals(dish, info.getDish());

        // también probar getNewEntry y setNewEntry
        Dish dish2 = new Dish("Ensalada", 5.0f, "Ensalada fresca", false, DishCategory.APPETIZER);
        info.setNewEntry(dish2);
        assertEquals(dish2, info.getNewEntry());
    }


}
