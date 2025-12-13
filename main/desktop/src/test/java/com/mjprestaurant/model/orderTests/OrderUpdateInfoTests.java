package com.mjprestaurant.model.orderTests;

import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.order.Order;
import com.mjprestaurant.model.order.OrderUpdateInfo;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderUpdateInfoTests {

    @Test
    void testDefaultConstructor() {
        OrderUpdateInfo info = new OrderUpdateInfo();
        assertNull(info.getSessionToken(), "Token debería ser null");
        assertNull(info.getItem(), "Item debería ser null");
    }

    @Test
    void testFullConstructor() {
        Order order = new Order(1L, 100L, LocalDateTime.now(), Order.Status.OPEN);
        OrderUpdateInfo info = new OrderUpdateInfo("token123", order);

        assertEquals("token123", info.getSessionToken());
        assertEquals(order, info.getItem());
    }

    @Test
    void testCopyConstructor() {
        Order order = new Order(2L, 200L, LocalDateTime.now(), Order.Status.SENDED);
        OrderUpdateInfo original = new OrderUpdateInfo("tokenABC", order);

        OrderUpdateInfo copy = new OrderUpdateInfo(original);
        assertEquals("tokenABC", copy.getSessionToken());
        assertEquals(order, copy.getItem());
    }

    @Test
    void testSettersAndGetters() {
        OrderUpdateInfo info = new OrderUpdateInfo();
        Order order = new Order(3L, 300L, LocalDateTime.now(), Order.Status.SERVED);

        info.setSessionToken("tokenXYZ");
        info.setItem(order);

        assertEquals("tokenXYZ", info.getSessionToken());
        assertEquals(order, info.getItem());
    }
}
