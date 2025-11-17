package com.mjprestaurant.model;

import com.mjprestaurant.model.dish.Dish;
import com.mjprestaurant.model.dish.Dish.DishCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para la clase Dish
 */
public class DishTests {

    @Test
    void testDefaultConstructor() {
        Dish d = new Dish();
        assertNull(d.getId(), "Id debería ser null");
        assertNull(d.getName(), "Nombre debería ser null");
        assertEquals(0.0f, d.getPrice(), "Precio debería ser 0.0");
        assertNull(d.getDescription(), "Descripción debería ser null");
        assertFalse(d.getAvailable(), "Disponible debería ser false por defecto");
        assertNull(d.getCategory(), "Categoría debería ser null");
    }

    @Test
    void testFullConstructor() {
        Dish d = new Dish("Pizza", 12.5f, "Pizza de jamón", true, DishCategory.MAIN);
        assertEquals("Pizza", d.getName());
        assertEquals(12.5f, d.getPrice());
        assertEquals("Pizza de jamón", d.getDescription());
        assertTrue(d.getAvailable());
        assertEquals(DishCategory.MAIN, d.getCategory());
    }

    @Test
    void testConstructorWithId() {
        Dish d = new Dish(1L, "Ensalada", 5.0f, "Ensalada fresca", false);
        assertEquals(1L, d.getId());
        assertEquals("Ensalada", d.getName());
        assertEquals(5.0f, d.getPrice());
        assertEquals("Ensalada fresca", d.getDescription());
        assertFalse(d.getAvailable());
        assertNull(d.getCategory(), "Categoría debería ser null si no se pasa");
    }

    @Test
    void testCopyConstructor() {
        Dish original = new Dish("Sopa", 3.5f, "Sopa caliente", true, DishCategory.APPETIZER);
        Dish copy = new Dish(original);
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getPrice(), copy.getPrice());
        assertEquals(original.getDescription(), copy.getDescription());
        assertEquals(original.getAvailable(), copy.getAvailable());
        assertEquals(original.getCategory(), copy.getCategory());
    }

    @Test
    void testSettersAndGetters() {
        Dish d = new Dish();
        d.setId(100L);
        d.setName("Hamburguesa");
        d.setPrice(8.5f);
        d.setDescription("Con queso y bacon");
        d.setAvailable(true);
        d.setCategory(DishCategory.MAIN);

        assertEquals(100L, d.getId());
        assertEquals("Hamburguesa", d.getName());
        assertEquals(8.5f, d.getPrice());
        assertEquals("Con queso y bacon", d.getDescription());
        assertTrue(d.getAvailable());
        assertEquals(DishCategory.MAIN, d.getCategory());
    }


}
