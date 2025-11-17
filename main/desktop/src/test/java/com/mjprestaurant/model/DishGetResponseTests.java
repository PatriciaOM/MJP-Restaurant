package com.mjprestaurant.model;

import com.mjprestaurant.model.dish.Dish;
import com.mjprestaurant.model.dish.Dish.DishCategory;
import com.mjprestaurant.model.dish.DishGetResponse;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para DishGetResponse
 */
public class DishGetResponseTests {

    @Test
    void testDefaultConstructor() {
        DishGetResponse response = new DishGetResponse();
        assertNull(response.getMessageStatus(), "El mensaje de estado debería ser null");
        assertNull(response.getDishes(), "La lista de platos debería ser null");
    }

    @Test
    void testFullConstructor() {
        List<Dish> list = new ArrayList<>();
        list.add(new Dish("Pizza", 12.5f, "Pizza de jamón", true, DishCategory.MAIN));
        DishGetResponse response = new DishGetResponse("OK", list);

        assertEquals("OK", response.getMessageStatus());
        assertEquals(list, response.getDishes());
        assertEquals(1, response.getDishes().size());
        assertEquals("Pizza", response.getDishes().get(0).getName());
    }

    @Test
    void testCopyConstructor() {
        List<Dish> list = new ArrayList<>();
        list.add(new Dish("Sopa", 3.5f, "Sopa caliente", true, DishCategory.APPETIZER));
        DishGetResponse original = new DishGetResponse("OK", list);

        DishGetResponse copy = new DishGetResponse(original);
        assertEquals("OK", copy.getMessageStatus());
        assertEquals(list, copy.getDishes());
    }

    @Test
    void testSettersAndGetters() {
        DishGetResponse response = new DishGetResponse();
        List<Dish> list = new ArrayList<>();
        list.add(new Dish("Ensalada", 5.0f, "Ensalada fresca", false, DishCategory.APPETIZER));

        response.setMessageStatus("SUCCESS");
        response.setDishes(list);

        assertEquals("SUCCESS", response.getMessageStatus());
        assertEquals(list, response.getDishes());
        assertEquals(1, response.getDishes().size());
        assertEquals("Ensalada", response.getDishes().get(0).getName());
    }

}
