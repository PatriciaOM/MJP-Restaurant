package com.mjprestaurant.model.dishTests;

import com.mjprestaurant.model.dish.Dish;
import com.mjprestaurant.model.dish.Dish.DishCategory;
import com.mjprestaurant.model.dish.DishUpdateInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para DishUpdateInfo
 */
public class DishUpdateInfoTests {

    @Test
    void testDefaultConstructor() {
        DishUpdateInfo info = new DishUpdateInfo();
        assertNull(info.getSessionToken(), "Token debería ser null");
        assertNull(info.getDish(), "Dish debería ser null");
        assertNull(info.getItem(), "Item debería ser null");
    }

    @Test
    void testFullConstructor() {
        Dish dish = new Dish("Pizza", 12.5f, "Pizza de jamón", true, DishCategory.MAIN);
        DishUpdateInfo info = new DishUpdateInfo("token123", dish);

        assertEquals("token123", info.getSessionToken());
        assertEquals(dish, info.getDish());
        assertEquals(dish, info.getItem());
    }

    @Test
    void testSettersAndGetters() {
        DishUpdateInfo info = new DishUpdateInfo();
        Dish dish = new Dish("Sopa", 3.5f, "Sopa caliente", true, DishCategory.APPETIZER);

        info.setSessionToken("tokenABC");
        info.setDish(dish);

        assertEquals("tokenABC", info.getSessionToken());
        assertEquals(dish, info.getDish());

        // también probar getItem y setItem
        Dish dish2 = new Dish("Ensalada", 5.0f, "Ensalada fresca", false, DishCategory.APPETIZER);
        info.setItem(dish2);
        assertEquals(dish2, info.getItem());
    }

}
