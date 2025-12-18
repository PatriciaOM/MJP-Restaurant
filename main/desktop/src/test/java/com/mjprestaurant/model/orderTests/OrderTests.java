package com.mjprestaurant.model.orderTests;

import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.order.Order;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests básicos para la clase Order
 */
public class OrderTests {

    @Test
    void testDefaultConstructor() {
        Order order = new Order();
        assertNull(order.getId());
        assertNull(order.getIdSessionService());
        assertNull(order.getDate());
        assertNull(order.getState());
    }

    @Test
    void testFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order(1L, 10L, now, Order.Status.OPEN);
        assertEquals(1L, order.getId());
        assertEquals(10L, order.getIdSessionService());
        assertEquals(now, order.getDate());
        assertEquals(Order.Status.OPEN, order.getState());
    }

    @Test
    void testPartialConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order(20L, now, Order.Status.SERVED);
        assertNull(order.getId());
        assertEquals(20L, order.getIdSessionService());
        assertEquals(now, order.getDate());
        assertEquals(Order.Status.SERVED, order.getState());
    }

    @Test
    void testCopyConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Order original = new Order(5L, now, Order.Status.SENDED);
        Order copy = new Order(original);
        assertNull(copy.getId()); // el constructor de copia no copia el id
        assertEquals(original.getIdSessionService(), copy.getIdSessionService());
        assertEquals(original.getDate(), copy.getDate());
        assertEquals(original.getState(), copy.getState());
    }

    @Test
    void testSettersAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order();
        order.setId(100L);
        order.setIdSessionService(200L);
        order.setDate(now);
        order.setState(Order.Status.OPEN);

        assertEquals(100L, order.getId());
        assertEquals(200L, order.getIdSessionService());
        assertEquals(now, order.getDate());
        assertEquals(Order.Status.OPEN, order.getState());
    }

}
